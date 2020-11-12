package dae.ujapack.entidades.puntosControl;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import javax.validation.Valid;

/**
 * Entidad oficina
 * @author juanc
 */
public class Oficina extends PuntoControl {
      
    /**Centro logístico asociado a la oficina*/
    @Valid
    private CentroLogistico centroAsociado;
    
    public Oficina(String id, CentroLogistico centroAsociado){
        this.id = id;
        this.centroAsociado = centroAsociado;
    }

    /**
     * @return the centroAsociado
     */
    public CentroLogistico getCentroAsociado() {
        return centroAsociado;
    }
}
