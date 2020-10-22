/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
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
    private ArrayList<String> conexiones;

    public CentroLogistico() {
        this.id = null;
        this.nombre = null;
        this.localizacion = null;
        this.conexiones = new ArrayList<>();
    }

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
    
    @Override
    public void actualizar(String idEnvio, LocalDate fecha, boolean inOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
