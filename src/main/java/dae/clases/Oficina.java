/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.clases;

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

    public void CreaEnvio(float alto, float ancho, float peso, String origen, String destino) {
        //Envio envio = new Envio(); Cuando esté creada la clase Envio.
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
    public void Actualizar(Number IdEnvio, LocalDate Fecha, boolean InOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
