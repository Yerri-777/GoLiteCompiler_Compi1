package ast;

import entorno.Entorno;

/**
 * NodoExpresionSentencia — Permite usar una expresión como sentencia.
 *
 * Ejemplo: fmt.Println("hola") es una expresión que se usa en contexto
 * de sentencia. Este nodo adapta NodoExpresion → NodoSentencia.
 */
public class NodoExpresionSentencia extends NodoSentencia {

    private final NodoExpresion expresion;

    public NodoExpresionSentencia(NodoExpresion expresion, int linea, int columna) {
        super(linea, columna);
        this.expresion = expresion;
    }

    @Override
    public Object execute(Entorno entorno) {
        expresion.getValue(entorno);  // evaluar y descartar el resultado
        return null;
    }

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "ExprSentencia:\n" + expresion.toAST(nivel + 1);
    }
}