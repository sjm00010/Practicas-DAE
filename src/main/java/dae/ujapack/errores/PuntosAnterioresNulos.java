package dae.ujapack.errores;

/**
 * Excepción provocada debido a que algun punto anterior en la ruta tiene fecha nula. 
 * @author juanc
 */
public class PuntosAnterioresNulos extends RuntimeException{
    public PuntosAnterioresNulos(String errorMessage){
        super(errorMessage);
    }
}
