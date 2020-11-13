package dae.ujapack.entidades.puntosControl;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad Centro logístico
 * @author juanc
 */
public class CentroLogistico extends PuntoControl {
        
    /** Nombre del centro logístico*/
    @NotBlank
    private String nombre;
    
    /** Ubicación del centro logístico*/
    @NotBlank
    private String localizacion;
    
    /** Conexiones asociadas a un centro logístico*/
    @NotNull @Size(min=1)
    private List<String> conexiones;

    public CentroLogistico(String id, String nombre, String localizacion, List<String> conexiones) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.conexiones = conexiones;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }

    /**
     * @return the conexiones
     */
    public List<String> getConexiones() {
        return conexiones;
    }
}
