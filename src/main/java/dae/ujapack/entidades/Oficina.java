package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;

/**
 *
 * @author juanc
 */
public class Oficina implements PuntoControl {
    
    private String nombre;
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
