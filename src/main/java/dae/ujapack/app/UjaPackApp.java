/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.app;

import dae.ujapack.entidades.Cliente;
import dae.ujapack.servicios.ServicioMensajeria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author sjm00010
 */
@SpringBootApplication(scanBasePackages = "dae.ujapack.servicios")
public class UjaPackApp {
    
    /*
    Dudas :
     - Quien crea el cliente? En principio es servicio?
     - 
    */
    
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
        
        ServicioMensajeria servicio = (ServicioMensajeria) context.getBean("servicioMensajeria");
        servicio.cargaDatos("D:\\Usuario\\Descargas\\redujapack.json", 10);
        
        servicio.creaEnvio(0, 0, 0, new Cliente(), new Cliente());
    }
}
