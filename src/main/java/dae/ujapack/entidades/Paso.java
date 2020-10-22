package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 *
 * @author juanc
 */
public class Paso {
    
    @PastOrPresent
    private LocalDate fecha;
    
    @NotEmpty
    private boolean inOut; // False entrada, True salida
    
    @NotNull
    private PuntoControl pasoPuntos;
    
        /**
     * Constructor parametrizado
     * @param pasoPuntos Parámetro con los puntos de control por los que ha pasado el envío.
     * @param inOut Indica la entrada(False) o salida(True) del punto
     * @param fecha Fecha del paso por el punto de control
     */
    public Paso(PuntoControl pasoPuntos, boolean inOut, LocalDate fecha){
        this.pasoPuntos = pasoPuntos;
        this.inOut = inOut;
        this.fecha = fecha;
    }
    
    /**
     * Función constructora parametrizada para cuando se crea la ruta.
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

