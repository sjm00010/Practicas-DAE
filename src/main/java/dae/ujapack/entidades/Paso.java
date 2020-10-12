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
     * @param pasoPuntos Parámetro con los puntos de control por los que ha pasado el envío.
     */
    public Paso(PuntoControl pasoPuntos){
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

