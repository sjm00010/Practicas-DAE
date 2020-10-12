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
    private boolean inOut; // False entrada, True salida
    private PuntoControl pasoPuntos;
    
    /**
     * Función constructora parametrizada de la clase.
     * @param pasoPuntos Parámetro con los puntos de control por los que ha pasado el envío.
     * @param inOut Indica la entrada(False) o salida(True) del punto
     */
    public Paso(PuntoControl pasoPuntos, boolean inOut){
        this.pasoPuntos = pasoPuntos;
        this.inOut = inOut;
    }
    
    /**
     * @return the fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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

