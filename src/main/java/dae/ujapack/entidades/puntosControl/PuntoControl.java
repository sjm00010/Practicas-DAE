package dae.ujapack.entidades.puntosControl;

import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un punto de control
 * @author sjm00010
 */
public abstract class PuntoControl {
    
    /* Id del punto de control */
    @NotBlank
    String id;
    
    public String getId(){ return id; }
}
