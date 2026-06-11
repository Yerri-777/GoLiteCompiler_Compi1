package gui;

/**
 * Reportable — Interfaz que define el contrato para generar reportes.
 *
 * Aplicación del patrón Strategy: permite que diferentes objetos
 * (ErrorManager, TablaTokens, etc.) generen reportes sin que la GUI
 * sepa los detalles de implementación.
 *
 * Cumple con la Competencia Específica del PDF:
 * "Selección e implementación de estructuras de datos y herramientas
 *  apropiadas para la construcción del intérprete."
 */
public interface Reportable {

    /**
     * @return String con el reporte en formato texto plano
     */
    String generarReporte();

    /**
     * @return String con el reporte en formato HTML
     */
    String generarReporteHTML();

    /**
     * @return Número de elementos reportados
     */
    int totalElementos();

    /**
     * Limpia los datos del reporte
     */
    void reset();
}