package dae.ujapack.app;

import dae.ujapack.repositorios.RepositorioCentroLogistico;
import dae.ujapack.repositorios.RepositorioOficina;
import dae.ujapack.utils.CargaDatos;
import dae.ujapack.utils.tuplas.OficinasCentrosServicioCarga;
import java.util.Arrays;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main
 *
 * @author sjm00010
 */
@SpringBootApplication(scanBasePackages = {"dae.ujapack.servicios", "dae.ujapack.repositorios"})
@EntityScan(basePackages = "dae.ujapack.entidades")
@EnableScheduling
@EnableCaching
public class UjaPackApp {

    private OficinasCentrosServicioCarga datos;

    @Autowired
    RepositorioOficina repositorioOficina;

    @Autowired
    RepositorioCentroLogistico repositorioCentroLogistico;

    @PostConstruct
    void cargarDatos() {
        if (repositorioCentroLogistico.isEmpty()) {
            datos = new CargaDatos().cargaDatos();
            repositorioCentroLogistico.guardar(datos.getCentros().values());
            repositorioOficina.guardar(datos.getOficinas().values());
        }
    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

    // El json esta en la carpeta del proyecto, se carga automaticamente
    public static void main(String[] args) throws Exception {
        // Creación de servidor
        SpringApplication servidor = new SpringApplication(UjaPackApp.class);
        ApplicationContext context = servidor.run(args);
    }
}
