package dae.ujapack.controladoresREST.DTOs;

import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;

/**
 *
 * @author sjm00010
 */
public class DTOLocalizadorPrecioEnvio {
    private final String identificador;
    private final Integer precio;

    public DTOLocalizadorPrecioEnvio(String identificador, Integer precio) {
        this.identificador = identificador;
        this.precio = precio;
    }

    public DTOLocalizadorPrecioEnvio(LocalizadorPrecioEnvio origin) {
        this.identificador = origin.getIdentificador();
        this.precio = origin.getPrecio();
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
