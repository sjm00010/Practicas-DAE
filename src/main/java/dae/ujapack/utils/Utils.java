package dae.ujapack.utils;

/**
 * Clase que almacena ciertas Utils de la aplicación
 * @author sjm00010
 */
public class Utils {

    public Utils() {
    }
    
    /** Enumeración con los posibles estados del envío */
    public enum Estado {
        EN_TRANSITO,
        EN_REPARTO,
        ENTREGADO,
        EXTRAVIADO
    }
}
