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
public interface PuntoControl {

    /**
     * Función que permite actualizar el envío.
     * @param idEnvio int Identificador del envio.
     * @param fecha LocalDate Anota la fecha de llegada o de salida del paquete en un punto de control.   
     * @param inOut boolean Parámetro el cual nos sirve para identificar si el envio llega o sale de un punto de control.
     */
    public void actualizar(int idEnvio, LocalDate fecha, boolean inOut);

}
