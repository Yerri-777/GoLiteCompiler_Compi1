package ast;

import entorno.Entorno;
import excepciones.BreakException;

/**
 * NodoBreak — Sentencia break.
 *
 * Lanza BreakException que NodoFor o NodoSwitch capturan.
 * Si se escapa de ambos → error semántico (lo valida el intérprete).
 */
public class NodoBreak extends NodoSentencia {

    public NodoBreak(int linea, int columna) {
        super(linea, columna);
    }

    @Override
    public Object execute(Entorno entorno) {
        throw new BreakException(linea, columna);
    }

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "Break\n";
    }
}