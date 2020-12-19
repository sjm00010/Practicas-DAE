package dae.ujapack.controladoresREST.DTOs;

import dae.ujapack.entidades.Envio;
import dae.ujapack.objetosvalor.Cliente;

/**
 *
 * @author sjm00010
 */
public class DTOEnvio {
    private String id;
    private int alto;
    private int ancho;
    private int peso;
    /** Localizador origen */
    private String origen;
    /** Localizador destino */
    private String destino;

    public DTOEnvio(String id, int alto, int ancho, int peso, String origen, String destino) {
        this.id = id;
        this.alto = alto;
        this.ancho = ancho;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }

    public DTOEnvio(Envio envio) {
        this(   envio.getId(),
                envio.getAlto(), 
                envio.getAncho(), 
                envio.getPeso(), 
                envio.getOrigen().getLocalizacion(), 
                envio.getDestino().getLocalizacion());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @return the origen
     */
    public String getOrigen() {
        return origen;
    }

    /**
     * @return the destino
     */
    public String getDestino() {
        return destino;
    }
    
    
}
