package ast;

import entorno.Entorno;
import excepciones.ErrorSemanticoException;
import java.util.List;

/**
 * NodoReflectTypeOf — reflect.TypeOf(expr)
 *
 * Devuelve el tipo de un valor en tiempo de ejecución como String.
 * Según el PDF:
 *   reflect.TypeOf(42)       → "int"
 *   reflect.TypeOf(3.14)     → "float64"
 *   reflect.TypeOf("hola")   → "string"
 *   reflect.TypeOf(true)     → "bool"
 *   reflect.TypeOf([]int{})  → "[]int"   (Fase 2)
 *   reflect.TypeOf(persona)  → "Persona" (Fase 2)
 */
public class NodoReflectTypeOf extends NodoExpresion {

    private final NodoExpresion argumento;

    public NodoReflectTypeOf(NodoExpresion argumento, int linea, int columna) {
        super(linea, columna);
        this.argumento = argumento;
    }

    @Override
    public Object getValue(Entorno entorno) {
        Object val = argumento.getValue(entorno);
        return tipoDe(val);
    }

    private String tipoDe(Object v) {
        if (v == null)            return "nil";
        if (v instanceof Integer) return "int";
        if (v instanceof Double)  return "float64";
        if (v instanceof String)  return "string";
        if (v instanceof Boolean) return "bool";
        if (v instanceof List)    return "[]interface";   // slices Fase 2
        return v.getClass().getSimpleName();
    }

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "ReflectTypeOf\n" + argumento.toAST(nivel + 1);
    }
}