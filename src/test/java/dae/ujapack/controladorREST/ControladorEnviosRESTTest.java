package dae.ujapack.controladorREST;

import dae.ujapack.controladoresREST.DTOs.DTOEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOLocalizadorPrecioEnvio;
import dae.ujapack.controladoresREST.DTOs.DTOPaso;
import dae.ujapack.controladoresREST.DTOs.DTOPuntoControlEstadoEnvio;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.servicios.ServicioLimpiadoBaseDatos;
import dae.ujapack.utils.Utils;
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
import org.springframework.test.context.TestPropertySource;

/**
 *
 * @author sjm00010
 */
@TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
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

    /**
     * Crear un TestRestTemplate para las pruebas
     */
    @PostConstruct
    void crearRestTemplate() {
        RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + localPort + "/ujapack")
                .additionalMessageConverters(List.of(springBootJacksonConverter));

        restTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    /**
     * Intento de creación de un envio inválido
     */
    @Test
    public void testAltaClienteInvalido() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        DTOEnvio envio = new DTOEnvio(5, 5, 5, cliente, null); // No tiene destino, es nulo.

        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta
                = restTemplate.withBasicAuth("admin", "admin")
                        .postForEntity("/envio", envio, DTOLocalizadorPrecioEnvio.class);

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testEnvioRutaCaso1() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");

        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, cliente, cliente);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta
                = restTemplate.withBasicAuth("admin", "admin")
                        .postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DTOPaso[] ruta = restTemplate.getForEntity(
                "/envio/{id}/puntoControl",
                DTOPaso[].class,
                respuesta.getBody().getIdentificador()
        ).getBody();

        /**
         * Compruebo que la ruta generada contiene los puntos que deberia ( 2
         * entrada/salida de la Oficina )
         */
        Assertions.assertThat(ruta).hasSize(2);
    }

    @Test
    public void testEnvioRutaCaso2() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");

        // Creación de envio con origen y destino en distintas oficinas dentro del mismo centro
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, origen, destino);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta
                = restTemplate.withBasicAuth("admin", "admin")
                        .postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DTOPaso[] ruta = restTemplate.getForEntity(
                "/envio/{id}/puntoControl",
                DTOPaso[].class,
                respuesta.getBody().getIdentificador()
        ).getBody();

        /**
         * Compruebo que la ruta generada contiene los puntos que deberia ( 2
         * entrada/salida de la Oficina origen + 2 entrada/salida del Centro
         * Logistico + 2 entrada/salida de la Oficina destino )
         */
        Assertions.assertThat(ruta).hasSize(6);
    }

    @Test
    public void testEnvioRutaCaso3() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Albacete");

        // Creación de envio con origen y destino en distintos centros
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, origen, destino);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta
                = restTemplate.withBasicAuth("admin", "admin")
                        .postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        DTOPaso[] ruta = restTemplate.getForEntity(
                "/envio/{id}/puntoControl",
                DTOPaso[].class,
                respuesta.getBody().getIdentificador()
        ).getBody();

        /**
         * Compruebo que la ruta generada contiene los puntos que deberia ( 2
         * entrada/salida de la Oficina + N entrada/salida del Centro
         * Logistico(4 -> 2 de Andalucía + 2 de La Mancha) + 2 entrada/salida de
         * la Oficina destino)
         */
        Assertions.assertThat(ruta).hasSize(8);
    }

    @Test
    public void testActualizaEnvioInvalido() {
        // Intento actualizar un envio que no existe
        ResponseEntity respuesta = restTemplate.withBasicAuth("operario", "secret")
                .exchange(
                        "/envio/{id}/puntoControl/{idPuntoControl}?isSalida={salida}", // Lo hago con exchange() y no con put() para comprobar el resultado para el test
                        HttpMethod.PUT,
                        HttpEntity.EMPTY,
                        Void.class,
                        "1234567890", // ID inexistente
                        "1",
                        "true"
                );

        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetSituacionEnvio() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");

        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
        DTOEnvio envioPrueba = new DTOEnvio(5, 5, 5, cliente, cliente);
        ResponseEntity<DTOLocalizadorPrecioEnvio> respuesta
                = restTemplate.withBasicAuth("admin", "admin")
                        .postForEntity("/envio", envioPrueba, DTOLocalizadorPrecioEnvio.class);
        Assertions.assertThat(respuesta.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Actualizo la salida de la oficina, por lo que el estado pasa e estar en reparto 
        restTemplate.withBasicAuth("operario", "secret")
                .put(
                        "/envio/{id}/puntoControl/{idPuntoControl}?isSalida={salida}",
                        null, // La informacion necesaria va en la URL, por lo que no es necesario enviar el objeto actualizado
                        respuesta.getBody().getIdentificador(),
                        "Almería", // Identificador del punto de control
                        "true" // Para saber si el punto de control es de salida o de entrada
                );

        DTOPuntoControlEstadoEnvio envioActualizado = restTemplate.getForEntity(
                "/envio/{id}/situacion",
                DTOPuntoControlEstadoEnvio.class,
                respuesta.getBody().getIdentificador()
        ).getBody();

        // Compruebo que esta en reparto, dado que sigue la oficina de origen
        Assertions.assertThat(envioActualizado.getEstado()).isEqualTo(Utils.Estado.EN_REPARTO);
    }

    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }

}
