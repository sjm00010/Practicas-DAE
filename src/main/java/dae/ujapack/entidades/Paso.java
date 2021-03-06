package dae.ujapack.entidades;

import dae.ujapack.entidades.puntosControl.PuntoControl;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

/**
 * Entidad que representa un punto de la ruta
 *
 * @author juanc
 */
@Entity
public class Paso implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @PastOrPresent
    private LocalDateTime fecha;

    // No se comprueba porque es un valor primitivo
    private boolean inOut; // False entrada, True salida

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "puntoControl")
    @NotNull
    private PuntoControl pasoPuntos;

    public Paso() {
        this.fecha = null; // Me aseguro de que la fecha se inicializa a null
    }

    /**
     * Constructor parametrizado
     *
     * @param pasoPuntos Parámetro con los puntos de control por los que ha
     * pasado el envío.
     * @param inOut Indica la entrada(False) o salida(True) del punto
     * @param fecha Fecha del paso por el punto de control
     */
    public Paso(PuntoControl pasoPuntos, boolean inOut, LocalDateTime fecha) {
        this.pasoPuntos = pasoPuntos;
        this.inOut = inOut;
        this.fecha = fecha;
    }

    /**
     * Función constructora parametrizada para cuando se crea la ruta.
     *
     * @param pasoPuntos Parámetro con los puntos de control por los que ha
     * pasado el envío.
     * @param inOut Indica la entrada(False) o salida(True) del punto
     */
    public Paso(PuntoControl pasoPuntos, boolean inOut) {
        this.pasoPuntos = pasoPuntos;
        this.inOut = inOut;
    }

    /**
     * @return the fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(LocalDateTime fecha) {
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
