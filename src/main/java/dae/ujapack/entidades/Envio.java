package dae.ujapack.entidades;

import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.constraints.*;

/**
 *
 * @author sjm00010
 */
public class Envio {
    
    @Size(min=10, max=10)
    private String id;
    
    @Positive
    private int alto;
    
    @Positive
    private int ancho;
    
    @Positive
    private int peso;
    
    
    @NotNull
    private Cliente origen; 
    
    @NotNull
    private Cliente destino;
    
    @NotNull
    private ArrayList<Paso> ruta;
    
    // Para no hacer mas engorrosa la creacion del envio el cliente ya viene creado, solo se vincula
    public Envio(String id, int alto, int ancho, int peso, Cliente origen, 
            Cliente destino, ArrayList<Paso> ruta) {
        this.id = id;
        this.alto = alto;
        this.ancho = ancho;
        this.peso = peso;
        this.origen = origen;
        this.destino = destino;
        this.ruta = ruta;
    }

    /**
     * @return the id
     */
    public String getId() {
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
    
    /**
     * @return the ruta
     */
    public ArrayList<Paso> getRuta() {
        return ruta;
    }
    
    /**
     * Función que calcula el precio de un envío
     * @return precio
     */
    public int getPrecio(){
        int numPc = 0;
        for (Paso paso : ruta) {
            if(paso.getPasoPuntos().getClass() == CentroLogistico.class)
                numPc++;
        }
        return peso*(alto*ancho)* (numPc+1) / 1000;
    }
    
    /**
     * Funcion que actualiza la fecha de un punto de la ruta
     * @param fecha Fecha que hay que actualizar
     * @param inOut Entrada o salida del Paso
     * @param pc Punto de control a actualizar
     */
    public void actualizar(LocalDate fecha, boolean inOut, PuntoControl pc){
        /* Se busca en los pasos aquel que tenga el PuntoControl igual al
           dado y que coincida con el valor de inOut para añadirle la fecha */
        for (Paso paso : ruta) {
            if(paso.getPasoPuntos().equals(pc) && paso.isInOut() == inOut){
                paso.setFecha(fecha);
            }
        }
    }
    
    /**
     * Función que devuelve el punto actual del envío
     * @return PuntoControl punto de control actual
     */
    public Paso getUltimoPunto(){
        Paso ultimoPunto = null;
        for (Paso paso : ruta) {
            if(paso.getFecha() != null){
                ultimoPunto = paso;
            }
        }
        return ultimoPunto;
    }
    
}
