package errores;

/**
 * Clasificación de errores según la fase del compilador donde se detectan.
 * Tal como especifica el PDF en la sección "Reporte de errores".
 */
public enum TipoError {
    LEXICO,     // Carácter no reconocido por el lexer
    SINTACTICO, // Estructura gramatical inválida
    SEMANTICO   // Incompatibilidad de tipos, variable no declarada, etc.
}