/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.clases;

import java.time.LocalDate;

/**
 *
 * @author juanc
 */
public class CentroLogistico implements PuntoControl {

    private Number id;
    private String nombre;
    private String localizacion;

    public CentroLogistico() {
        this.id = 0;
        this.nombre = null;
        this.localizacion = null;

    }

    public CentroLogistico(Number id, String nombre, String localizacion) {
        this.id = id;
        this.nombre = nombre;
        this.localizacion = localizacion;
    }

    @Override
    public void Actualizar(Number IdEnvio, LocalDate Fecha, boolean InOut) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the id
     */
    public Number getId() {
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
}
