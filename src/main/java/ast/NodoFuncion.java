package ast;

import entorno.Entorno;
import excepciones.ReturnException;
import excepciones.ErrorSemanticoException;
import errores.ErrorManager;
import java.util.List;

/**
 * NodoFuncion — Declaración de una función GoLite.
 *
 * Cubre la sintaxis:
 *   func nombre(params) tipoRetorno { bloque }
 *   func nombre(params)             { bloque }
 *
 * En Fase 1 solo se ejecuta main(). Las funciones user-defined
 * se invocan desde NodoLlamadaFuncion en Fase 2 completa.
 *
 * Los parámetros se pasan por valor (primitivos) o por referencia
 * (structs/slices), según el PDF.
 */
public class NodoFuncion extends Nodo {

    private final String              nombre;
    private final List<NodoParametro> parametros;
    private final NodoTipo            tipoRetorno;   // null si es void
    private final NodoBloque          cuerpo;

    public NodoFuncion(String nombre,
                       List<NodoParametro> parametros,
                       NodoTipo tipoRetorno,
                       NodoBloque cuerpo,
                       int linea, int columna) {
        super(linea, columna);
        this.nombre      = nombre;
        this.parametros  = parametros;
        this.tipoRetorno = tipoRetorno;
        this.cuerpo      = cuerpo;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public String              getNombre()      { return nombre;      }
    public List<NodoParametro> getParametros()  { return parametros;  }
    public NodoTipo            getTipoRetorno() { return tipoRetorno; }
    public NodoBloque          getCuerpo()      { return cuerpo;      }
    public boolean             esVoid()         { return tipoRetorno == null; }

    // ─── Ejecución ─────────────────────────────────────────────────────────────

    /**
     * Ejecuta la función sin argumentos (para main()).
     * Con argumentos se usa ejecutarCon(entorno, args).
     */
    @Override
    public Object execute(Entorno entorno) {
        return ejecutarCon(entorno, null);
    }

    /**
     * Ejecuta la función con una lista de valores para los parámetros.
     *
     * @param entornoLlamada Entorno desde donde se llama (para resolver los args)
     * @param valores        Lista de valores evaluados para los parámetros
     * @return Valor de retorno (null si es void)
     */
    public Object ejecutarCon(Entorno entornoLlamada, List<Object> valores) {
        // Crear entorno local con el nombre de la función para el reporte de símbolos
        Entorno entornoLocal = new Entorno(entornoLlamada, nombre);

        // Enlazar parámetros con valores
        if (valores != null) {
            if (valores.size() != parametros.size()) {
                throw new ErrorSemanticoException(
                    "La función '" + nombre + "' espera " + parametros.size() +
                    " argumento(s) pero recibió " + valores.size() + ".",
                    linea, columna
                );
            }
            for (int i = 0; i < parametros.size(); i++) {
                NodoParametro p = parametros.get(i);
                entornoLocal.declarar(
                    p.getNombre(),
                    p.getTipo().getNombre(),
                    valores.get(i)
                );
            }
        }

        // Ejecutar el cuerpo capturando Return
        try {
            cuerpo.execute(entornoLocal);
        } catch (ReturnException ret) {
            return ret.getValor();
        }

        return null;
    }

    // ─── AST ───────────────────────────────────────────────────────────────────

    @Override
    public String toAST(int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(nivel)).append("Funcion: ").append(nombre);
        if (tipoRetorno != null) sb.append(" -> ").append(tipoRetorno.getNombre());
        sb.append("\n");

        for (NodoParametro p : parametros) {
            sb.append(p.toAST(nivel + 1));
        }
        sb.append(cuerpo.toAST(nivel + 1));
        return sb.toString();
    }
}