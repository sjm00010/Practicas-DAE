/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.entidades;

import dae.ujapack.interfaces.PuntoControl;
import java.time.LocalDate;

/**
 *
 * @author juanc
 */
public class CentroLogistico implements PuntoControl {

    private int id;
    private String nombre;
    private String localizacion;

    public CentroLogistico() {
        this.id = 0;
        this.nombre = null;
        this.localizacion = null;

    }

    public CentroLogistico(int id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    /**
     * @return the id
     */
    public int getId() {
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
    
    @Override
    public void actualizar(int idEnvio, LocalDate fecha, boolean inOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
