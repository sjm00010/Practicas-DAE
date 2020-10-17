/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.app;

import dae.ujapack.entidades.Cliente;
import dae.ujapack.interfaces.PuntoControl;
import dae.ujapack.servicios.ServicioCarga;
import dae.ujapack.servicios.ServicioMensajeria;
import java.time.LocalDate;
import javafx.util.Pair;
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
    Mejoras:
     - Identificar al repartidor dentro de la ruta, con un nombre por dejecto. EJ. String id = "Repartidor";
     - Que la interfaz tenga los IDs como atributo (en caso de que se haga el punto anterior)
    */
    
    public static void main(String[] args) throws Exception {
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
        
        ServicioMensajeria servicio = (ServicioMensajeria) context.getBean("servicioMensajeria");
        ServicioCarga carga = (ServicioCarga) context.getBean("servicioCarga");
        // 
        
        Pair<String, Integer> envio = servicio.creaEnvio(10, 50, 40, new Cliente("Almería"), new Cliente("Zamora"));
        servicio.actualizar(envio.getKey(), LocalDate.now(), true, "4");
        Pair<PuntoControl, String> situacion = servicio.obtenerSituacion(envio.getKey());
        System.out.println("El envio "+envio.getKey()+" esta : "+situacion.getValue()+" . Ultimo PC : "+situacion.getKey());
    }
}
