package gui;

import javax.swing.*;

/**
 * ComponenteGUI — Clase abstracta base para componentes de la GUI del IDE GoLite.
 *
 * Implementa el patrón Template Method y Herencia según los principios
 * de POO del PDF del proyecto (Competencias de herencia y polimorfismo).
 *
 * Cada componente visual debe inicializar su interfaz de forma específica,
 * pero todos siguen el mismo patrón: crear UI y establecer configuración.
 */
public abstract class ComponenteGUI extends JPanel {

    protected static final int ANCHO_DEFECTO  = 1200;
    protected static final int ALTO_DEFECTO   = 750;

    /**
     * Constructor que sigue el patrón Template Method.
     * Primero configura, luego construye la UI.
     */
    public ComponenteGUI() {
        setLayout(null);  // Será sobrescrito por subclases
        inicializar();
    }

    /**
     * Método plantilla que define la secuencia de inicialización.
     * Subclases implementan configurar() y construirUI() específicamente.
     */
    private void inicializar() {
        configurar();
        construirUI();
    }

    /**
     * Configurar propiedades del componente.
     * Método abstracto a implementar por subclases.
     */
    protected abstract void configurar();

    /**
     * Construir la interfaz visual.
     * Método abstracto a implementar por subclases.
     */
    protected abstract void construirUI();

    /**
     * Método para refrescar la UI (aplicable en todas las subclases).
     * Implementa polimorfismo: cada subclase decide qué refrescar.
     */
    public abstract void refrescar();
}