package dae.ujapack.controladoresREST.DTOs;

import dae.ujapack.entidades.Paso;
import java.time.LocalDateTime;

/**
 *
 * @author sjm00010
 */
public class DTOPaso {
    private LocalDateTime fecha;
    private boolean inOut; // False entrada, True salida
    private String idPuntoControl;

    public DTOPaso(LocalDateTime fecha, boolean inOut, String idPuntoControl) {
        this.fecha = fecha;
        this.inOut = inOut;
        this.idPuntoControl = idPuntoControl;
    }

    public DTOPaso(Paso paso) {
        this.fecha = paso.getFecha();
        this.inOut = paso.isInOut();
        this.idPuntoControl = paso.getPasoPuntos().getId();
    }
    

    /**
     * @return the fecha
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * @return the inOut
     */
    public boolean isInOut() {
        return inOut;
    }

    /**
     * @return the idPuntoControl
     */
    public String getIdPuntoControl() {
        return idPuntoControl;
    }
    
}
