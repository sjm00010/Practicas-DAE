package dae.ujapack.app;

import dae.ujapack.repositorios.RepositorioPuntoControl;
import dae.ujapack.utils.CargaDatos;
import dae.ujapack.utils.tuplas.OficinasCentrosServicioCarga;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main
 *
 * @author sjm00010
 */
@SpringBootApplication(scanBasePackages = {
    "dae.ujapack.servicios",
    "dae.ujapack.repositorios",
    "dae.ujapack.controladoresREST",
    "dae.ujapack.seguridad"})
@EntityScan(basePackages = "dae.ujapack.entidades")
@EnableScheduling
@EnableCaching
public class UjaPackApp {

    private OficinasCentrosServicioCarga datos;

    @Autowired
    RepositorioPuntoControl repositorioPuntoControl;

    @PostConstruct
    void cargarDatos() {
        if (repositorioPuntoControl.isEmpty()) {
            datos = new CargaDatos().cargaDatos();
            repositorioPuntoControl.guardar(datos.getCentros().values());
            repositorioPuntoControl.guardar(datos.getOficinas().values());
        }
    }

    // No creo los beans de la cache, uso la notación equivalente en el aplication.yml
    // El json esta en la carpeta del proyecto, se carga automaticamente
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
    }
}
