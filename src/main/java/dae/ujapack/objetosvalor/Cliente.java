package dae.ujapack.objetosvalor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * Objeto valor Cliente
 * @author sjm00010
 */
public class Cliente {
    
    @Pattern(regexp="\\d{8}[A-HJ-NP-TV-Z]")
    private String dni;
    
    @NotBlank
    private String nombre;
    
    @NotBlank
    private String apellidos;
    
    @NotBlank
    private String localizacion;
    
    public Cliente(String dni, String nombre, String apellidos, String localizacion) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.localizacion = localizacion;
    }

    /**
     * @return the dni
     */
    public String getDni() {
        return dni;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @return the localizacion
     */
    public String getLocalizacion() {
        return localizacion;
    }
}
