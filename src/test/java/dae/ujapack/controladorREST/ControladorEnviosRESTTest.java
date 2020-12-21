package dae.ujapack.controladorREST;

import dae.ujapack.controladoresREST.DTOs.DTOEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOLocalizadorPrecioEnvio;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.servicios.ServicioLimpiadoBaseDatos;
import java.util.List;
import javax.annotation.PostConstruct;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.annotation.DirtiesContext;

/**
 *
 * @author sjm00010
 */
@SpringBootTest(classes = dae.ujapack.app.UjaPackApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext // Necesario para que no haya conflictos con la cache
public class ControladorEnviosRESTTest {
    @Autowired
    ServicioLimpiadoBaseDatos limpiadorBaseDatos;
    
    @LocalServerPort
    int localPort; 

    @Autowired
    MappingJackson2HttpMessageConverter springBootJacksonConverter;

    TestRestTemplate restTemplate;
    
    /** Crear un TestRestTemplate para las pruebas */
    @PostConstruct
    void crearRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(List.of(springBootJacksonConverter));
        
        restTemplate = new TestRestTemplate(restTemplateBuilder);
    } 
    
    /** Intento de creación de un envio inválido */
    @Test
    public void testAltaClienteInvalido() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");        
        DTOEnvio envio = new DTOEnvio(5, 5, 5, cliente, null); // No tiene destino, es nulo.
 
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta = 
                restTemplate.postForEntity("/envio", envio, DTOLocalizadorPrecioEnvio.class);
        
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
    
    @Test
    public void testEnvioRutaCaso1() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, cliente, cliente);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta = 
                restTemplate.postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<DTOEnvio> respuesta2 = 
                restTemplate.getForEntity("/envio/{id}", DTOEnvio.class, 
                        respuesta.getBody().getIdentificador());
        
        DTOEnvio envio = respuesta2.getBody();
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina ) */
        Assertions.assertThat(envio.getRuta().size()).isEqualTo(2);  
    }
    
    @Test
    public void testEnvioRutaCaso2() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con origen y destino en distintas oficinas dentro del mismo centro
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, origen, destino);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta = 
                restTemplate.postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<DTOEnvio> respuesta2 = 
                restTemplate.getForEntity("/envio/{id}", DTOEnvio.class, 
                        respuesta.getBody().getIdentificador());
        
        DTOEnvio envio = respuesta2.getBody();
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina origen + 2 entrada/salida del Centro Logistico +
         *   2 entrada/salida de la Oficina destino ) */
        Assertions.assertThat(envio.getRuta().size()).isEqualTo(6);  
    }
    
    @Test
    public void testEnvioRutaCaso3() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Albacete");
        
        // Creación de envio con origen y destino en distintos centros
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, origen, destino);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta = 
                restTemplate.postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ResponseEntity<DTOEnvio> respuesta2 = 
                restTemplate.getForEntity("/envio/{id}", DTOEnvio.class, 
                        respuesta.getBody().getIdentificador());
        
        DTOEnvio envio = respuesta2.getBody();
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina + 
         *   N entrada/salida del Centro Logistico(4 -> 2 de Andalucía + 2 de La Mancha) +
         *   2 entrada/salida de la Oficina destino) */
        Assertions.assertThat(envio.getRuta().size()).isEqualTo(8); 
    }
    
    @Test
    public void testActualizaEnvioInvalido() {
        // Intento actualizar un envio que no existe
        ResponseEntity respuesta = restTemplate.exchange("/envio/{id}/{idPuntoControl}",
                        HttpMethod.PUT,
                        HttpEntity.EMPTY,
                        Void.class,
                        "1234567890", // ID inexistente
                        "1"
                        );
        
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
    
//    @Test
//    public void testGetSituacionEnvio() {
//        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
//        
//        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
//        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, cliente, cliente);
//        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta = 
//                restTemplate.postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
//        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        
//        ResponseEntity<DTOEnvio> respuesta2 = 
//                restTemplate.getForEntity("/envio/{id}", DTOEnvio.class, 
//                        respuesta.getBody().getIdentificador());
//        DTOEnvio envio = new DTOEnvio(5, 5, 5, cliente, cliente);
//        
//        servicioUjapack.actualizar(envio.getIdentificador(), LocalDateTime.now(), true, "Almería");
//                
//        // Compruebo que esta en reparto, dado que sigue la oficina de origen
//        Assertions.assertThat(servicioUjapack.obtenerSituacion(envio.getIdentificador()).getEstado()).isEqualTo(Utils.Estado.EN_REPARTO);  
//    }

    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }

}
