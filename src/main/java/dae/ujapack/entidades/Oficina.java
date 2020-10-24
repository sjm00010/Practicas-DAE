package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * Entidad oficina
 * @author juanc
 */
public class Oficina implements PuntoControl {
    
    /**Nombre de la oficina*/
    @NotBlank
    private String id;
    
    /**Centro logístico asociado a la oficina*/
    @Valid
    private CentroLogistico centroAsociado;
    
    public Oficina(String id, CentroLogistico centroAsociado){
        this.id = id;
        this.centroAsociado = centroAsociado;
    }

    /**
     * @return the nombre
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @return the centroAsociado
     */
    public CentroLogistico getCentroAsociado() {
        return centroAsociado;
    }
}
