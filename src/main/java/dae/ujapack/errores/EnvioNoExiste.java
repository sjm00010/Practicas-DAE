package dae.ujapack.errores;

/**
 *
 * @author sjm00010
 */
public class EnvioNoExiste extends RuntimeException {
    public EnvioNoExiste(String errorMessage) {
        super(errorMessage);
    }
}
