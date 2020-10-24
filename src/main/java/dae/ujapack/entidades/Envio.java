package dae.ujapack.entidades;

import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.Valid;
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
    
    
    @Valid
    private Cliente origen; 
    
    @Valid
    private Cliente destino;
    
    @NotNull
    private ArrayList<@Valid Paso> ruta;
    
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
        return peso*(alto*ancho)* (ruta.size()/2+1) / 1000;
    }
    
    /**
     * Funcion que actualiza la fecha de un punto de la ruta
     * @param fecha Fecha que hay que actualizar
     * @param inOut Entrada o salida del Paso
     * @param pc Punto de control a actualizar
     */
    public void actualizar(LocalDate fecha, boolean inOut, PuntoControl pc) throws IdPuntoControlInvalido{
        /* Se busca en los pasos aquel que tenga el PuntoControl igual al
           dado y que coincida con el valor de inOut para añadirle la fecha */
        Paso encontrado = ruta.stream()
                            .filter(paso -> paso.getFecha() == null || (paso.getPasoPuntos().equals(pc) && paso.isInOut() == inOut))
                            .findFirst().orElseThrow(() -> new IdPuntoControlInvalido("Se intenta actualizar un punto que no esta en la ruta"));

        if(encontrado.getPasoPuntos().equals(pc)){
            encontrado.setFecha(fecha);
        }else{
            throw new RuntimeException();
        }
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
