package ast;

import entorno.Entorno;
import java.util.List;

/**
 * NodoBloque — Bloque de sentencias delimitado por { }.
 *
 * Crea un nuevo Entorno hijo para implementar el scoping del PDF:
 * "Las variables declaradas dentro de un bloque solo son accesibles
 *  dentro de ese bloque y en bloques anidados."
 *
 * Las variables del bloque ocultan las del ámbito superior con el
 * mismo nombre (shadowing), como indica el PDF.
 */
public class NodoBloque extends NodoSentencia {

    private final List<NodoSentencia> sentencias;

    public NodoBloque(List<NodoSentencia> sentencias, int linea, int columna) {
        super(linea, columna);
        this.sentencias = sentencias;
    }

    public List<NodoSentencia> getSentencias() { return sentencias; }

    @Override
    public Object execute(Entorno entornoParent) {
        // Cada bloque tiene su propio entorno hijo
        Entorno entornoLocal = new Entorno(entornoParent);

        for (NodoSentencia s : sentencias) {
            s.execute(entornoLocal);
            // NOTA: Las excepciones BreakException, ContinueException y
            // ReturnException se propagan hacia arriba sin ser atrapadas aquí.
            // Cada nodo que las genera (NodoFor, NodoFuncion) las captura.
        }

        return null;
    }

    @Override
    public String toAST(int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(nivel)).append("Bloque\n");
        for (NodoSentencia s : sentencias) {
            sb.append(s.toAST(nivel + 1));
        }
        return sb.toString();
    }
}