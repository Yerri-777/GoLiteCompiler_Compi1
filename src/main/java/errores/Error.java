package errores;

/**
 * Modela un error detectado durante cualquier fase del compilador GoLite.
 *
 * Contiene exactamente los campos que pide el reporte del PDF:
 *   No. | Descripción | Línea | Columna | Tipo
 */
public class Error {

    private final TipoError tipo;
    private final String    descripcion;
    private final int       linea;
    private final int       columna;

    /**
     * @param tipo        Fase del compilador donde se detectó
     * @param descripcion Mensaje descriptivo del error
     * @param linea       Línea en el archivo fuente (base 1)
     * @param columna     Columna en el archivo fuente (base 1)
     */
    public Error(TipoError tipo, String descripcion, int linea, int columna) {
        this.tipo        = tipo;
        this.descripcion = descripcion;
        this.linea       = linea;
        this.columna     = columna;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public TipoError getTipo()        { return tipo;        }
    public String    getDescripcion() { return descripcion; }
    public int       getLinea()       { return linea;       }
    public int       getColumna()     { return columna;     }

    // ─── Representación ────────────────────────────────────────────────────────

    /**
     * Formato para la consola del IDE GoLite.
     */
    @Override
    public String toString() {
        return String.format("[ERROR %s] Línea %d, Col %d: %s",
                tipo.name(), linea, columna, descripcion);
    }

    /**
     * Formato para la fila del reporte HTML/tabla según el PDF.
     * Columnas: No. | Descripción | Línea | Columna | Tipo
     */
    public String toReportRow(int numero) {
        return String.format("| %-4d | %-45s | %-6d | %-7d | %-10s |",
                numero, descripcion, linea, columna, tipo.name().toLowerCase());
    }
}