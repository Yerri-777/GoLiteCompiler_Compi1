package ast;

import entorno.Entorno;

/**
 * NodoTipo — Representa un tipo de dato en el AST.
 *
 * Usado en declaraciones de variables y parámetros de funciones.
 * El nombre puede ser: "int", "float64", "string", "bool", "rune",
 * o el nombre de un struct (Fase 2).
 */
public class NodoTipo extends Nodo {

    private final String nombre;

    public NodoTipo(String nombre, int linea, int columna) {
        super(linea, columna);
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }

    /** Retorna el valor por defecto del tipo según el PDF. */
    public Object valorPorDefecto() {
        switch (nombre) {
            case "int":     return 0;
            case "float64": return 0.0;
            case "string":  return "";
            case "bool":    return false;
            case "rune":    return 0;
            default:        return null;  // nil para tipos compuestos
        }
    }

    @Override
    public Object execute(Entorno entorno) { return nombre; }

    @Override
    public String toAST(int nivel) {
        return indent(nivel) + "Tipo: " + nombre + "\n";
    }
}