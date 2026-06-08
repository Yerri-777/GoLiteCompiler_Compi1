package excepciones;

/**
 * ContinueException
 *
 * Se lanza cuando el intérprete encuentra una sentencia "continue".
 * El NodoFor la atrapa para saltar a la siguiente iteración.
 *
 * Si se propaga fuera de un bucle → error semántico.
 */
public class ContinueException extends RuntimeException {

    private final int linea;
    private final int columna;

    public ContinueException(int linea, int columna) {
        super("continue");
        this.linea   = linea;
        this.columna = columna;
    }

    public int getLinea()   { return linea;   }
    public int getColumna() { return columna; }
}