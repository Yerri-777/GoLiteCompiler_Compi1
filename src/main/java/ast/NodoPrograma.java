package ast;

import entorno.Entorno;
import excepciones.ErrorSemanticoException;
import errores.ErrorManager;
import java.util.List;

/**
 * NodoPrograma — Raíz del AST.
 *
 * Contiene la lista de declaraciones globales (funciones).
 * Su execute():
 *   1. Registra todas las funciones en el entorno global
 *   2. Busca y ejecuta func main()
 */
public class NodoPrograma extends Nodo {

    private final List<Nodo> declaraciones;

    public NodoPrograma(List<Nodo> declaraciones, int linea, int columna) {
        super(linea, columna);
        this.declaraciones = declaraciones;
    }

    public List<Nodo> getDeclaraciones() { return declaraciones; }

    @Override
    public Object execute(Entorno entorno) {
        // Paso 1: Registrar todas las funciones en el entorno global
        // antes de ejecutar para permitir llamadas hacia adelante.
        for (Nodo d : declaraciones) {
            if (d instanceof NodoFuncion) {
                NodoFuncion fn = (NodoFuncion) d;
                try {
                    entorno.declararFuncion(fn.getNombre(), fn);
                } catch (ErrorSemanticoException e) {
                    ErrorManager.getInstance().agregarSemantico(
                        e.getDescripcion(), e.getLinea(), e.getColumna()
                    );
                }
            }
        }

        // Paso 2: Buscar y ejecutar main()
        NodoFuncion main = entorno.buscarFuncion("main");
        if (main == null) {
            ErrorManager.getInstance().agregarSemantico(
                "No se encontró la función 'main'. El programa no tiene punto de entrada.",
                linea, columna
            );
            return null;
        }

        try {
            main.execute(entorno);
        } catch (ErrorSemanticoException e) {
            ErrorManager.getInstance().agregarSemantico(
                e.getDescripcion(), e.getLinea(), e.getColumna()
            );
        }

        return null;
    }

    @Override
    public String toAST(int nivel) {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(nivel)).append("Programa\n");
        for (Nodo d : declaraciones) {
            sb.append(d.toAST(nivel + 1));
        }
        return sb.toString();
    }
}