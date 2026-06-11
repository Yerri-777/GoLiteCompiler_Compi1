package ast;

import entorno.Entorno;
import excepciones.BreakException;
import excepciones.ContinueException;
import excepciones.ErrorSemanticoException;

/**
 * NodoFor — Sentencia for.
 *
 * Fase 1 cubre dos formas (el for range queda para Fase 2):
 *
 *   1. For clásico:   for init; cond; inc { bloque }
 *      init != null, condicion != null, incremento != null
 *
 *   2. For condición: for cond { bloque }    (equivale a while)
 *      init == null, condicion != null, incremento == null
 *
 * Manejo de break/continue mediante excepciones Java:
 *   - BreakException    → termina el bucle
 *   - ContinueException → salta al siguiente ciclo
 */
public class NodoFor extends NodoSentencia {

    private final NodoSentencia  inicializacion;  // null en for-condición
    private final NodoExpresion  condicion;
    private final NodoSentencia  incremento;      // null en for-condición
    private final NodoBloque     cuerpo;

    public NodoFor(NodoSentencia inicializacion,
                   NodoExpresion condicion,
                   NodoSentencia incremento,
                   NodoBloque    cuerpo,
                   int linea, int columna) {
        super(linea, columna);
        this.inicializacion = inicializacion;
        this.condicion      = condicion;
        this.incremento     = incremento;
        this.cuerpo         = cuerpo;
    }

    @Override
    public Object execute(Entorno entorno) {
        // Crear entorno para el bloque del for
        // La inicialización vive en este entorno
        Entorno entornoFor = new Entorno(entorno);

        // Ejecutar inicialización (for clásico)
        if (inicializacion != null) {
            inicializacion.execute(entornoFor);
        }

        // Bucle principal
        while (true) {
            // Evaluar condición
            Object valCond = condicion.getValue(entornoFor);

            if (!(valCond instanceof Boolean)) {
                throw new ErrorSemanticoException(
                    "La condición del 'for' debe ser de tipo bool. " +
                    "Se recibió: " + tipoDe(valCond) + ".",
                    linea, columna
                );
            }

            if (!(Boolean) valCond) break;  // condición falsa → salir

            // Ejecutar cuerpo
            try {
                cuerpo.execute(entornoFor);
            } catch (BreakException b) {
                // break → terminar el for
                break;
            } catch (ContinueException c) {
                // continue → saltar al incremento y seguir
                // (no hacemos break, el incremento se ejecuta abajo)
            }

            // Ejecutar incremento (for clásico)
            if (incremento != null) {
                incremento.execute(entornoFor);
            }
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
        sb.append(indent(nivel)).append("For\n");
        if (inicializacion != null) {
            sb.append(indent(nivel + 1)).append("Init:\n");
            sb.append(inicializacion.toAST(nivel + 2));
        }
        sb.append(indent(nivel + 1)).append("Condicion:\n");
        sb.append(condicion.toAST(nivel + 2));
        if (incremento != null) {
            sb.append(indent(nivel + 1)).append("Incremento:\n");
            sb.append(incremento.toAST(nivel + 2));
        }
        sb.append(indent(nivel + 1)).append("Cuerpo:\n");
        sb.append(cuerpo.toAST(nivel + 2));
        return sb.toString();
    }
}