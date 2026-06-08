package compilador;

import gui.MainFrame;
import javax.swing.SwingUtilities;

/**
 * Main — Punto de entrada del IDE GoLite.
 *
 * Lanza la interfaz gráfica en el Event Dispatch Thread (EDT)
 * como exige Swing para thread-safety.
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}