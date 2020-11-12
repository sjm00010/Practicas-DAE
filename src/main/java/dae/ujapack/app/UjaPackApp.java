package dae.ujapack.app;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import dae.ujapack.servicios.ServicioCarga;
import dae.ujapack.servicios.ServicioEnrutado;
import dae.ujapack.servicios.ServicioMensajeria;
import dae.ujapack.tuplas.OficinasCentrosServicioCarga;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Main
 * @author sjm00010
 */
@SpringBootApplication(scanBasePackages = "dae.ujapack.servicios")
@EntityScan(basePackages="dae.ujapack.entidades")
public class UjaPackApp {
    private OficinasCentrosServicioCarga datos;
        
    UjaPackApp() {
        datos = new ServicioCarga().cargaDatos();
    }
    
    @Bean
    ServicioEnrutado grafo() {
        ServicioEnrutado enrutado = new ServicioEnrutado((ArrayList<CentroLogistico>) 
                                            datos.getCentros()
                                            .values()
                                            .stream()
                                            .collect(Collectors.toList())
                                    );
        return enrutado;
    }
    
    @Bean
    @Primary // https://www.baeldung.com/spring-qualifier-annotation
    ServicioMensajeria servicioUjapack() {
        ServicioMensajeria mensajeria = new ServicioMensajeria( datos.getOficinas(), datos.getCentros() );
        return mensajeria;
    }
    
    // El json esta en la carpeta del proyecto, se carga automaticamente
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
    }
}
