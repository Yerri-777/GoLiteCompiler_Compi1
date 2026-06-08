package excepciones;

/**
 * ErrorSemanticoException
 *
 * Se lanza durante el recorrido del AST cuando se detecta un error semántico:
 *   - Variable no declarada
 *   - Tipos incompatibles en operación
 *   - División por cero
 *   - Break/Continue fuera de un ciclo
 *   - Acceso a nil
 *   - Argumento inválido en función embebida
 *
 * El Compiler.java la captura, la registra en ErrorManager y continúa
 * la ejecución cuando sea posible (modo recuperación de errores).
 */
public class ErrorSemanticoException extends RuntimeException {

    private final String descripcion;
    private final int    linea;
    private final int    columna;

    public ErrorSemanticoException(String descripcion, int linea, int columna) {
        super(descripcion);
        this.descripcion = descripcion;
        this.linea       = linea;
        this.columna     = columna;
    }

    public String getDescripcion() { return descripcion; }
    public int    getLinea()       { return linea;       }
    public int    getColumna()     { return columna;     }
}