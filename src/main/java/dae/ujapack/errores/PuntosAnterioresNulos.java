/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.errores;

/**
 * Excepción provocada debido a que los puntos anteriores son nulos. 
 * @author juanc
 */
public class PuntosAnterioresNulos extends Exception{
    public PuntosAnterioresNulos(String errorMessage){
        super(errorMessage);
    }
    
}
