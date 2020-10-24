package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 *
 * @author juanc
 */
public class Oficina implements PuntoControl {
    
    /**Nombre de la oficina*/
    @NotNull
    private String nombre;
    /**Centro logístico asociado a la oficina*/
    @NotNull
    private CentroLogistico centroAsociado;

    public Oficina() {
        this.nombre = null;
        this.centroAsociado = null;
    }
    
    public Oficina(String nombre, CentroLogistico centroAsociado){
        this.nombre = nombre;
        this.centroAsociado = centroAsociado;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the centroAsociado
     */
    public CentroLogistico getCentroAsociado() {
        return centroAsociado;
    }
}
