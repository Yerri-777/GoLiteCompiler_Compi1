package ast;

import entorno.Entorno;

/**
 * Nodo — Clase abstracta raíz de toda la jerarquía del AST GoLite.
 *
 * POO aplicada:
 * - Herencia: todos los nodos extienden esta clase
 * - Polimorfismo: execute() y getValue() se sobreescriben en cada subclase
 * - Encapsulamiento: línea y columna son protected para subclases
 *
 * Cada nodo conoce su posición en el código fuente
 * para reportar errores con línea y columna exactas.
 */
public abstract class Nodo {

    protected final int linea;
    protected final int columna;

    protected Nodo(int linea, int columna) {
        this.linea   = linea;
        this.columna = columna;
    }

    // ─── Getters ───────────────────────────────────────────────────────────────

    public int getLinea()   { return linea;   }
    public int getColumna() { return columna; }

    // ─── Métodos Abstractos ────────────────────────────────────────────────────

    /**
     * Ejecuta este nodo en el entorno dado.
     * Implementado por nodos de sentencia (NodoSentencia y subclases).
     * Los nodos de expresión sobrescriben getValue() en su lugar.
     *
     * @param entorno Entorno actual de ejecución (tabla de símbolos)
     * @return Object Resultado de la ejecución (suele ser null para sentencias)
     */
    public abstract Object execute(Entorno entorno);

    /**
     * Genera la representación del nodo para el Reporte AST (Día 4).
     * Cada subclase describe sus hijos para construir el árbol visual.
     *
     * @param nivel Profundidad del nodo en el árbol (para indentación)
     * @return String con la representación en texto del nodo
     */
    public abstract String toAST(int nivel);

    // ─── Utilidad para indentación del AST ─────────────────────────────────────

    protected String indent(int nivel) {
        return "  ".repeat(nivel);
    }
}