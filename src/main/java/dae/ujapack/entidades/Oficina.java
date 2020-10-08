/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;

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

    public void creaEnvio(float alto, float ancho, float peso, 
            String origen, String destino) {
        
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

    @Override
    public void actualizar(int idEnvio, LocalDate fecha, boolean inOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
