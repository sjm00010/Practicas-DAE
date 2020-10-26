package dae.ujapack.entidades;

import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.errores.PuntosAnterioresNulos;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Entidad envio
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
    
    @Valid
    private Cliente origen; 
    
    @Valid
    private Cliente destino;
    
    @NotNull
    private ArrayList<@Valid Paso> ruta;
    
    // Para no hacer mas engorrosa la creación del envio el cliente ya viene creado, solo se vincula
    public Envio(String id, int alto, int ancho, int peso, Cliente origen, 
            Cliente destino, ArrayList<@Valid Paso> ruta) {
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
        return peso*(alto*ancho)* (ruta.size()/2+1) / 1000;
    }
    
    /**
     * Funcion que actualiza la fecha de un punto de la ruta
     * @param fecha Fecha que hay que actualizar
     * @param inOut Entrada o salida del Paso
     * @param pc Punto de control a actualizar
     */
    public void actualizar(LocalDateTime fecha, boolean inOut, PuntoControl pc){
        /* Se busca en los pasos aquel que tenga el PuntoControl igual al
           dado y que coincida con el valor de inOut para añadirle la fecha */
        if(pc == null)
            throw new IdPuntoControlInvalido("Punton de control nulo");
        
        boolean anterior = false, esta = false;
        for (Paso paso : ruta) {
            if(paso.getPasoPuntos().getId().equals(pc.getId()) && paso.isInOut() == inOut){
                paso.setFecha(fecha);
                esta = true;
                break;
            }else if(paso.getFecha() == null ){
                anterior = true;
            }
        }
        
        if (!esta)
            throw new IdPuntoControlInvalido("Se intenta actualizar un punto que no esta en la ruta");
        else if (anterior)
            throw new PuntosAnterioresNulos("Algun punto anterior no ha sido actualizado");
    }
    
    /**
     * Función que devuelve el punto actual del envío
     * @return PuntoControl punto de control actual
     */
    public Paso getUltimoPunto(){    
        return ruta.stream()
                    .reduce(null, (anterior, actual) -> actual.getFecha() == null ? anterior : actual);
    }
}
