package dae.ujapack.controladoresREST.DTOs;

import dae.ujapack.utils.Utils.Estado;

/**
 *
 * @author sjm00010
 */
public class DTOPuntoControlEstadoEnvio {

    // Guardo el ID del punto de control que es el dato que interesa devolver
    private String idPC;
    private Estado estado;

    public DTOPuntoControlEstadoEnvio(String idPC, Estado estado) {
        this.idPC = idPC;
        this.estado = estado;
    }

    /**
     * @return the idPC
     */
    public String getIdPC() {
        return idPC;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }

}
