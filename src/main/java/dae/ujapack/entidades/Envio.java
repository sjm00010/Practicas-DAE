/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.entidades;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author sjm00010
 */
public class Envio {
    private int id;
    private int alto;
    private int ancho;
    private int peso;
    private Cliente origen;
    private Cliente destino;
    // FALTA el Array de Pasos, añadir tras su implementación
    // private ArrayList<Paso> ruta;

    public Envio() {
    }

    // Modificar tras añadir ruta
    public Envio(int id, int alto, int ancho, int peso, Cliente origen, Cliente destino) {
        this.id = id;
        this.alto = alto;
        this.ancho = ancho;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the alto
     */
    public int getAlto() {
        return alto;
    }

    /**
     * @return the ancho
     */
    public int getAncho() {
        return ancho;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @return the origen
     */
    public Cliente getOrigen() {
        return origen;
    }

    /**
     * @return the destino
     */
    public Cliente getDestino() {
        return destino;
    }
    
    // Añadir getRuta tras añadir ruta
    
    /**
     * Función que calcula el precio de un envío
     * @return precio
     */
    public int calculaPrecio(){
        // Completar cuando se implementen la clase Paso
        return peso*(alto*ancho); // * pasos.numPC / 100
    }
    
    /**
     * 
     * @param fecha
     * @param inOut 
     */
    public void actualizar(LocalDate fecha, boolean inOut){ // PuntoControl pc,
        /* Se busca en los pasos aquel que tenga el PuntoControl igual al
           dado y que coincida con el valor de inOut para añadirle la fecha */
    }
    
}
