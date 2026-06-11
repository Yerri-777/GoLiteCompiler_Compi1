package ast;

import entorno.Entorno;
import excepciones.ErrorSemanticoException;

/**
 * NodoIf — Sentencia if / else if / else.
 *
 * Según el PDF:
 *   - La condición debe retornar bool; si no, error semántico
 *   - Los paréntesis en la condición son opcionales
 *   - Puede haber cualquier cantidad de else if anidados
 *
 * Estructura del nodo:
 *   condicion  → NodoExpresion (debe evaluar a Boolean)
 *   bloqueIf   → NodoBloque
 *   bloqueElse → NodoBloque | null
 *   elseIf     → NodoIf     | null  (cadena de else if)
 */
public class NodoIf extends NodoSentencia {

    private final NodoExpresion condicion;
    private final NodoBloque    bloqueIf;
    private final NodoBloque    bloqueElse;  // null si no hay else
    private final NodoIf        elseIf;      // null si no hay else if

    public NodoIf(NodoExpresion condicion,
                  NodoBloque    bloqueIf,
                  NodoBloque    bloqueElse,
                  NodoIf        elseIf,
                  int linea, int columna) {
        super(linea, columna);
        this.condicion  = condicion;
        this.bloqueIf   = bloqueIf;
        this.bloqueElse = bloqueElse;
        this.elseIf     = elseIf;
    }

    @Override
    public Object execute(Entorno entorno) {
        Object valCond = condicion.getValue(entorno);

        // La condición DEBE ser bool
        if (!(valCond instanceof Boolean)) {
            throw new ErrorSemanticoException(
                "La condición del 'if' debe ser de tipo bool. " +
                "Se recibió: " + tipoDe(valCond) + ".",
                linea, columna
            );
        }

        if ((Boolean) valCond) {
            bloqueIf.execute(entorno);
        } else if (elseIf != null) {
            elseIf.execute(entorno);
        } else if (bloqueElse != null) {
            bloqueElse.execute(entorno);
        }

        return null;
    }

    private String tipoDe(Object v) {
        if (v instanceof Integer) return "int";
        if (v instanceof Double)  return "float64";
        if (v instanceof Boolean) return "bool";
        if (v instanceof String)  return "string";
        return "nil";
    }

    @Override
    public String toAST(int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(nivel)).append("If\n");
        sb.append(indent(nivel + 1)).append("Condicion:\n");
        sb.append(condicion.toAST(nivel + 2));
        sb.append(indent(nivel + 1)).append("Then:\n");
        sb.append(bloqueIf.toAST(nivel + 2));
        if (elseIf != null) {
            sb.append(indent(nivel + 1)).append("ElseIf:\n");
            sb.append(elseIf.toAST(nivel + 2));
        }
        if (bloqueElse != null) {
            sb.append(indent(nivel + 1)).append("Else:\n");
            sb.append(bloqueElse.toAST(nivel + 2));
        }
        return sb.toString();
    }
}