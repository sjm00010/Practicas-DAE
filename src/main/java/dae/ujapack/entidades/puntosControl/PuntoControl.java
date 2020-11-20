package dae.ujapack.entidades.puntosControl;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotBlank;

/**
 * Clase que representa un punto de control
 * @author sjm00010
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PuntoControl implements Serializable {
    
    /* Id del punto de control */
    @Id
    @NotBlank
    String id;
    
    public PuntoControl() {}
    public PuntoControl(String id) {this.id = id;}
    public String getId(){ return id; }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PuntoControl other = (PuntoControl) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}
