/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.interfaces;

import java.time.LocalDate;

/**
 *
 * @author juanc
 */
public interface PuntoControl {

    public void actualizar(int idEnvio, LocalDate fecha, boolean inOut);

}
