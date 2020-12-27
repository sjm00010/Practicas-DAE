package dae.ujapack.errores;

/**
 * Clase que representa un error con el identificador de un punto de control
 *
 * @author sjm00010
 */
public class IdPuntoControlInvalido extends RuntimeException {

    public IdPuntoControlInvalido(String errorMessage) {
        super(errorMessage);
    }
}
