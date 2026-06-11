package entorno;

import ast.NodoFuncion;
import errores.ErrorManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Entorno — Tabla de símbolos encadenada.
 * Blindado: Tolerante a errores, reporta al ErrorManager sin lanzar excepciones críticas.
 */
public class Entorno {

    // ─── Centinela para "no encontrado" ───────────────────────────────────────
    public static final Object NO_ENCONTRADO = new Object();

    // ─── Estado del entorno ───────────────────────────────────────────────────
    private final Map<String, Simbolo> tabla;
    private final Entorno              padre;
    private final String               nombreAmbito;

    // ─── Solo en el entorno global ─────────────────────────────────────────────
    private final Map<String, NodoFuncion> funciones;
    private final StringBuilder            salidaConsola;
    private final List<Simbolo>            historicoSimbolos;

    // ─── Constructor para entorno global ──────────────────────────────────────
    public Entorno() {
        this.tabla             = new HashMap<>();
        this.padre             = null;
        this.nombreAmbito      = "Global";
        this.funciones         = new HashMap<>();
        this.salidaConsola     = new StringBuilder();
        this.historicoSimbolos = new ArrayList<>();
    }

    // ─── Constructor para entorno hijo (función, bloque) ──────────────────────
    public Entorno(Entorno padre) {
        this.tabla             = new HashMap<>();
        this.padre             = padre;
        this.nombreAmbito      = padre != null ? padre.getNombreAmbito() : "Global";
        this.funciones         = null;
        this.salidaConsola     = null;
        this.historicoSimbolos = null;
    }

    // ─── Constructor para entorno de función (tiene nombre propio) ────────────
    public Entorno(Entorno padre, String nombreFuncion) {
        this.tabla             = new HashMap<>();
        this.padre             = padre;
        this.nombreAmbito      = nombreFuncion != null ? nombreFuncion : "Funcion_Desconocida";
        this.funciones         = null;
        this.salidaConsola     = null;
        this.historicoSimbolos = null;
    }

    // ─── Getters simples ──────────────────────────────────────────────────────
    public String  getNombreAmbito() { return nombreAmbito; }
    public Entorno getPadre()        { return padre;        }

    // ══════════════════════════════════════════════════════════════════════════
    // VARIABLES
    // ══════════════════════════════════════════════════════════════════════════

    public void declarar(String nombre, String tipo, Object valor) {
        declarar(nombre, tipo, valor, 0, 0); // Redirige a la versión completa
    }

    public void declarar(String nombre, String tipo, Object valor, int linea, int col) {
        if (tabla.containsKey(nombre)) {
            // [CAMBIO ROBUSTO] Reportar y continuar (No lanzar Exception)
            ErrorManager.getInstance().agregarSemantico(
                "La variable '" + nombre + "' ya fue declarada en este ámbito.", linea, col
            );
            return; // Detenemos la declaración, pero no el compilador
        }
        
        Simbolo s = new Simbolo(
            nombre, Simbolo.TipoSimbolo.VARIABLE, tipo,
            nombreAmbito, linea, col, valor
        );
        tabla.put(nombre, s);
        agregarHistorico(s);
    }

    public Object obtener(String nombre) {
        if (tabla.containsKey(nombre)) return tabla.get(nombre).getValor();
        if (padre != null) return padre.obtener(nombre);
        return NO_ENCONTRADO;
    }

    public String obtenerTipo(String nombre) {
        if (tabla.containsKey(nombre)) return tabla.get(nombre).getTipoDato();
        if (padre != null) return padre.obtenerTipo(nombre);
        return "desconocido"; // Retorno más seguro que null
    }

    public void asignar(String nombre, Object valor) {
        if (tabla.containsKey(nombre)) {
            tabla.get(nombre).setValor(valor);
            return;
        }
        if (padre != null) {
            padre.asignar(nombre, valor);
        }
    }

    public boolean existeEnActual(String nombre) {
        return tabla.containsKey(nombre);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // FUNCIONES
    // ══════════════════════════════════════════════════════════════════════════

    public void declararFuncion(String nombre, NodoFuncion funcion) {
        Entorno global = getGlobal();
        if (global.funciones.containsKey(nombre)) {
            // [CAMBIO ROBUSTO] Reportar y continuar
            ErrorManager.getInstance().agregarSemantico(
                "La función '" + nombre + "' ya está declarada.", 
                funcion.getLinea(), funcion.getColumna()
            );
            return;
        }
        
        global.funciones.put(nombre, funcion);
        
        String tipoRetorno = funcion.getTipoRetorno() != null ? funcion.getTipoRetorno().getNombre() : "void";
        Simbolo s = new Simbolo(
            nombre, Simbolo.TipoSimbolo.FUNCION, tipoRetorno,
            "Global", funcion.getLinea(), funcion.getColumna(), null
        );
        global.agregarHistorico(s);
    }

    public NodoFuncion buscarFuncion(String nombre) {
        return getGlobal().funciones.get(nombre);
    }

    // ══════════════════════════════════════════════════════════════════════════
    // SALIDA DE CONSOLA E HISTÓRICO
    // ══════════════════════════════════════════════════════════════════════════

    public void agregarSalida(String texto) {
        if (texto != null) {
            getGlobal().salidaConsola.append(texto);
        }
    }

    public String getSalidaConsola() {
        return getGlobal().salidaConsola.toString();
    }

    private void agregarHistorico(Simbolo s) {
        getGlobal().historicoSimbolos.add(s);
    }

    public List<Simbolo> getHistoricoSimbolos() {
        return getGlobal().historicoSimbolos;
    }

    private Entorno getGlobal() {
        Entorno e = this;
        while (e.padre != null) e = e.padre;
        return e;
    }
}