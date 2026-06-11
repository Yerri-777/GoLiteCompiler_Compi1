package ast;

import entorno.Entorno;

/**
 * NodoExpresion — Nodo abstracto para expresiones que producen un valor.
 *
 * Hereda de Nodo y agrega el contrato getValue(Entorno).
 * execute() delega en getValue() para que las expresiones
 * puedan usarse tanto en contexto de sentencia como de valor.
 *
 * Subclases directas:
 *   - NodoLiteral         → un valor constante
 *   - NodoIdentificador   → lectura de variable
 *   - NodoBinario         → operación de dos operandos
 *   - NodoUnario          → negación o NOT
 *   - NodoLlamadaFuncion  → llamada que retorna valor
 *   - NodoFmtPrintln      → función embebida (retorna null)
 *   - NodoStrconvAtoi     → conversión string→int
 *   - NodoStrconvParseFloat → conversión string→float64
 *   - NodoReflectTypeOf   → tipo como string
 */
public abstract class NodoExpresion extends Nodo {

    protected NodoExpresion(int linea, int columna) {
        super(linea, columna);
    }

    /**
     * Evalúa la expresión y retorna su valor.
     * El tipo de retorno es Object para soportar int, double, String, Boolean, etc.
     *
     * @param entorno Entorno actual de ejecución
     * @return El valor resultante de la expresión
     */
    public abstract Object getValue(Entorno entorno);

    /**
     * execute() de una expresión simplemente evalúa su valor.
     * Así se puede usar un NodoExpresion donde se espera un Nodo.
     */
    @Override
    public Object execute(Entorno entorno) {
        return getValue(entorno);
    }
}