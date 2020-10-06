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

    public Oficina(String nombre, CentroLogistico centroAsociado) {
        this.nombre = nombre;
        this.centroAsociado = centroAsociado;
    }

    /**
     * Función que establece el envio para un paquete.
     *
     * @param alto Parámetro con la altura del paquete.
     * @param ancho Parámetro con la anchura del paquete.
     * @param peso Parámetro con el peso del paquete.
     * @param origen Parámetro que nos indica el remitente del paquete.
     * @param destino Parámetro que nos indica el destinatario del paquete.
     */
    public void creaEnvio(float alto, float ancho, float peso, Cliente origen, Cliente destino) {

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

    /**
     * Función que permite actualizar el envío.
     *
     * @param idEnvio int Identificador del envio.
     * @param fecha LocalDate Anota la fecha de llegada o de salida del paquete en un punto de control.
     * @param inOut boolean Parámetro el cual nos sirve para identificar si el envio llega o sale de un punto de control.
     */
    @Override
    public void actualizar(int idEnvio, LocalDate fecha, boolean inOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
