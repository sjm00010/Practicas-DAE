package dae.ujapack.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

/**
 * Main
 * @author sjm00010
 */
@SpringBootApplication(scanBasePackages = "dae.ujapack.servicios")
@EntityScan(basePackages="dae.ujapack.entidades")
public class UjaPackApp {
    // El json esta en la carpeta del proyecto, se carga automaticamente
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
    }
}
