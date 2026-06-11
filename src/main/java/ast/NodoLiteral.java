package ast;

import entorno.Entorno;

/**
 * NodoLiteral — Valor constante en el código fuente.
 *
 * Tipos soportados según el PDF:
 *   int     → Integer (Java)
 *   float64 → Double  (Java)
 *   string  → String  (Java)
 *   bool    → Boolean (Java)
 *   rune    → Integer (Java) — valor ASCII del carácter
 *   nil     → null
 *
 * El literal 0 puede ser int o float64 según contexto (PDF lo indica).
 */
public class NodoLiteral extends NodoExpresion {

    private final Object valor;
    private final String tipo;

    public NodoLiteral(Object valor, String tipo, int linea, int columna) {
        super(linea, columna);
        this.valor = valor;
        this.tipo  = tipo;
    }

    public Object getValor() { return valor; }
    public String getTipo()  { return tipo;  }

    @Override
    public Object getValue(Entorno entorno) {
        return valor;
    }

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "Literal(" + tipo + "): " + valor + "\n";
    }
}