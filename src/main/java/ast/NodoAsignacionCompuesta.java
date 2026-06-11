package ast;

import entorno.Entorno;
import errores.ErrorManager;

/**
 * NodoAsignacionCompuesta — id += expr  |  id -= expr
 * Blindado: Verifica la existencia de la variable. El resto de la seguridad 
 * está garantizada al delegar la operación en NodoBinario.
 */
public class NodoAsignacionCompuesta extends NodoSentencia {

    private final String        nombre;
    private final String        operador;   // "+=" o "-="
    private final NodoExpresion expresion;

    public NodoAsignacionCompuesta(String nombre, String operador,
                                   NodoExpresion expresion,
                                   int linea, int columna) {
        super(linea, columna);
        this.nombre    = nombre;
        this.operador  = operador;
        this.expresion = expresion;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public String getNombre() {
        return nombre;
    }

    public String getOperador() {
        return operador;
    }

    public NodoExpresion getExpresion() {
        return expresion;
    }

    // ─── Ejecución ─────────────────────────────────────────────────────────────

    @Override
    public Object execute(Entorno entorno) {
        // Verificar que la variable exista
        Object valorActual = entorno.obtener(nombre);
        if (valorActual == Entorno.NO_ENCONTRADO) {
            ErrorManager.getInstance().agregarSemantico(
                "La variable '" + nombre + "' no está declarada en este ámbito.",
                linea, columna
            );
            return null; // Abortar asignación compuesta
        }

        String opSimple = "+=".equals(operador) ? "+" : "-";

        // Delegar la operación en NodoBinario (el cual ya es tolerante a fallos)
        NodoBinario operacion = new NodoBinario(
            opSimple,
            new NodoIdentificador(nombre, linea, columna),
            expresion,
            linea, columna
        );

        Object nuevoValor = operacion.getValue(entorno);
        
        // Si NodoBinario no falló (es decir, devolvió un resultado válido), asignamos
        if (nuevoValor != null) {
            entorno.asignar(nombre, nuevoValor);
        }
        
        return null;
    }

    // ─── AST ───────────────────────────────────────────────────────────────────

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "AsignCompuesta: " + nombre + " " + operador + "\n" +
               expresion.toAST(nivel + 1);
    }
}