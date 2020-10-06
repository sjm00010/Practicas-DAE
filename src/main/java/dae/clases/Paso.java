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
public class Paso {

    
    private LocalDate fecha;
    private boolean inOut;
    private PuntoControl pasoPuntos;
    
    public Paso() {
        this.fecha = null;
        this.inOut = false;
        this.pasoPuntos = null;

    }
    //Necesaria la clase Envio
    
    /**
     * Función constructora parametrizada de la clase.
     * 
     * @param fecha Parámetro que anota la fecha de llegada de un paquete cuando llega a un punto de control y cuando sale al siguiente punto de control anota su fecha de salida.
     * @param inOut Parámetro el cual nos sirve para identificar si el envio llega o sale de un punto de control.
     * @param pasoPuntos Parámetro con los puntos de control por los que ha pasado el envío.
     */
    public Paso(LocalDate fecha, boolean inOut,PuntoControl pasoPuntos){
        this.fecha = fecha;
        this.inOut = inOut;
        this.pasoPuntos = pasoPuntos;
    }
    
    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @return the inOut
     */
    public boolean isInOut() {
        return inOut;
    }

    /**
     * @return the pasoPuntos
     */
    public PuntoControl getPasoPuntos() {
        return pasoPuntos;
    }
    
}

