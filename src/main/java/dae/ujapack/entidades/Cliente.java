package dae.ujapack.entidades;

/**
 *
 * @author sjm00010
 */
public class Cliente {
    private String dni;
    private String nombre;
    private String apellidos;
    private String localizacion;

    public Cliente() {
    }

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
