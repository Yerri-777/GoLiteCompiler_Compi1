package excepciones;

/**
 * ReturnException
 *
 * Se lanza cuando el intérprete encuentra una sentencia "return".
 * El NodoFuncion la atrapa y extrae el valor de retorno.
 *
 * Preparado para Fase 2 (declaración de funciones).
 * En Fase 1 solo se usa internamente para salir de main().
 *
 * Lleva el valor retornado como Object para soportar cualquier tipo.
 */
public class ReturnException extends RuntimeException {

    private final Object valor;
    private final int    linea;
    private final int    columna;

    /**
     * Return con valor (para funciones con tipo de retorno).
     */
    public ReturnException(Object valor, int linea, int columna) {
        super("return");
        this.valor   = valor;
        this.linea   = linea;
        this.columna = columna;
    }

    /**
     * Return vacío (para funciones void).
     */
    public ReturnException(int linea, int columna) {
        this(null, linea, columna);
    }

    public Object getValor()    { return valor;   }
    public int    getLinea()    { return linea;   }
    public int    getColumna()  { return columna; }
    public boolean tieneValor() { return valor != null; }
}