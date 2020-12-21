package dae.ujapack.controladoresREST.DTOs;

import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Paso;
import dae.ujapack.objetosvalor.Cliente;
import java.util.List;
import java.util.stream.Collectors;

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
    private Cliente origen;
    /** Localizador destino */
    private Cliente destino;
    /** Ruta del envio */
    private List<DTOPaso> ruta;

    // Contructor para crear el envío
    public DTOEnvio(int alto, int ancho, int peso, Cliente origen, Cliente destino) {
        this.id = ""; // En el momento de la creación no me interesa el id, se le asignará durante la misma
        this.alto = alto;
        this.ancho = ancho;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }

    // Contructor para devolver un envio
    public DTOEnvio(Envio envio) {
        this(   envio.getAlto(), 
                envio.getAncho(), 
                envio.getPeso(),
                envio.getOrigen(), 
                envio.getDestino());
        // En el caso de que el envio ya este creado si me interesa tener el id
        this.id = envio.getId(); 
        this.ruta = envio.getRuta().stream().map(paso -> {
            return new DTOPaso(paso);
        }).collect(Collectors.toList());
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
    public Cliente getOrigen() {
        return origen;
    }

    /**
     * @return the destino
     */
    public Cliente getDestino() {
        return destino;
    }

    /**
     * @return the ruta
     */
    public List<DTOPaso> getRuta() {
        return ruta;
    }
    
}
