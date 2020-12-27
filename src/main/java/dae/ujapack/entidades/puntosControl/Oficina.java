package dae.ujapack.entidades.puntosControl;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

/**
 * Entidad oficina
 *
 * @author juanc
 */
@Entity
public class Oficina extends PuntoControl implements Serializable {

    /**
     * Centro logístico asociado a la oficina
     */
    @ManyToOne
    @JoinColumn(name = "CentroAsociado")
    @Valid
    private CentroLogistico centroAsociado;

    public Oficina() {
    }

    public Oficina(String id, CentroLogistico centroAsociado) {
        super(id);
        this.centroAsociado = centroAsociado;
    }

    /**
     * @return the centroAsociado
     */
    public CentroLogistico getCentroAsociado() {
        return centroAsociado;
    }
}
