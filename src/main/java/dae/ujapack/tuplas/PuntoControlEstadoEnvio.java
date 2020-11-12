package dae.ujapack.tuplas;

import dae.ujapack.entidades.puntosControl.PuntoControl;
import dae.ujapack.utils.util.Estado;

/**
 * Clase que para almacenar una tupla del último punto de control y el estado
 * de un envío
 * @author sjm00010
 */
public class PuntoControlEstadoEnvio {
    private PuntoControl pc;
    private Estado estado;

    public PuntoControlEstadoEnvio(PuntoControl pc, Estado estado) {
        this.pc = pc;
        this.estado = estado;
    }

    /**
     * @return the pc
     */
    public PuntoControl getPc() {
        return pc;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return estado;
    }
}
