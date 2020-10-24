package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entidad Centro logístico
 * @author juanc
 */
public class CentroLogistico implements PuntoControl {
    
    /** Identificador del centro logístico*/
    @NotNull
    private String id;
    
    /** Nombre del centro logístico*/
    @NotBlank
    private String nombre;
    
    /** Ubicación del centro logístico*/
    @NotBlank
    private String localizacion;
    
    /** Conexiones asociadas a un centro logístico*/
    @NotNull
    @Size(min=1)
    private ArrayList<String> conexiones;

    public CentroLogistico(String id, String nombre, String localizacion, ArrayList<String> conexiones) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
        this.conexiones = conexiones;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
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
    public ArrayList<String> getConexiones() {
        return conexiones;
    }
}
