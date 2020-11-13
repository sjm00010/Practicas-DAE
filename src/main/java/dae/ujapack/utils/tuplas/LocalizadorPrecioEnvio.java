package dae.ujapack.utils.tuplas;

/**
 * Clase que para almacenar una tupla del localizado y el precio de un envío
 * @author sjm00010
 */
public class LocalizadorPrecioEnvio {
    private final String identificador;
    private final Integer precio;

    public LocalizadorPrecioEnvio(String identificador, Integer precio) {
        this.identificador = identificador;
        this.precio = precio;
    }

    /**
     * @return the identificador
     */
    public String getIdentificador() {
        return identificador;
    }

    /**
     * @return the precio
     */
    public Integer getPrecio() {
        return precio;
    }
    
    
}
