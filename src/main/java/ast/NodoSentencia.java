package ast;

import entorno.Entorno;

/**
 * NodoSentencia — Nodo abstracto para sentencias que ejecutan acciones.
 *
 * A diferencia de NodoExpresion, las sentencias no retornan valor
 * (su execute() siempre retorna null).
 *
 * Subclases:
 *   - NodoBloque              → { sentencias... }
 *   - NodoDeclaracionVar      → var id tipo = expr  |  id := expr
 *   - NodoAsignacion          → id = expr
 *   - NodoAsignacionCompuesta → id += expr  |  id -= expr
 *   - NodoIncrementoDecremento→ id++  |  id--
 *   - NodoIf                  → if/else
 *   - NodoFor                 → for clásico / while
 *   - NodoBreak               → break
 *   - NodoContinue            → continue
 *   - NodoExpresionSentencia  → expresión usada como sentencia
 */
public abstract class NodoSentencia extends Nodo {

    protected NodoSentencia(int linea, int columna) {
        super(linea, columna);
    }
}