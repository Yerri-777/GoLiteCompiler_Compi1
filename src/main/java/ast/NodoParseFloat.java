package ast;

import entorno.Entorno;
import errores.ErrorManager;

/**
 * Función embebida: strconv.ParseFloat(cadena)
 */
public class NodoParseFloat { // Implementa tu interfaz Expresion
    
    private final Object expresion; // Debería ser de tipo Expresion o Nodo
    private final int linea;
    private final int columna;

    public NodoParseFloat(Object expresion, int linea, int columna) {
        this.expresion = expresion;
        this.linea = linea;
        this.columna = columna;
    }

    // Método resolver o evaluate (ajusta el nombre según tu interfaz)
    public Object resolver(Entorno env) {
        // Asumiendo que tu interfaz tiene un método resolver() o evaluate()
        // Object valor = ((Expresion) expresion).resolver(env); 
        Object valor = null; // Reemplazar por la evaluación real

        if (valor == null) return 0.0;

        if (valor instanceof String) {
            try {
                return Double.parseDouble((String) valor);
            } catch (NumberFormatException e) {
                ErrorManager.getInstance().agregarSemantico(
                    "strconv.ParseFloat: La cadena '" + valor + "' no tiene un formato numérico válido.", 
                    linea, columna
                );
                return 0.0; // Valor seguro para evitar cascada de errores
            }
        } else {
            ErrorManager.getInstance().agregarSemantico(
                "strconv.ParseFloat requiere un argumento de tipo string.", 
                linea, columna
            );
            return 0.0;
        }
    }
}