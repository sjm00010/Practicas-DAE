package dae.ujapack.servicios;

import dae.ujapack.errores.EnvioNoExiste;
import dae.ujapack.objetosvalor.Cliente;
import javafx.util.Pair;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;
import dae.ujapack.utils.util;
import java.time.LocalDateTime;
import javax.validation.ConstraintViolationException;

/**
 * Test para ServicioMensajeria
 * @author sjm00010
 */
@SpringBootTest(classes = dae.ujapack.app.UjaPackApp.class)
public class ServicioMensajeriaTest {
        
    @Autowired
    ServicioMensajeria servicioUjapack;

    @Test
    public void testAccesoServicioUjapack() {
        Assertions.assertThat(servicioUjapack).isNotNull();
    }
    
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testCreaEnvioInvalido() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");        
        
        Assertions.assertThatThrownBy(() -> {
            servicioUjapack.creaEnvio(5, 5, 5, cliente, null); }) // No tiene destino, es nulo.
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testGetSituacionEnvio() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        Pair<String, Integer> envio = servicioUjapack.creaEnvio(5, 5, 5, cliente, cliente);
        
        // Compruebo que esta en transito, dado que sigue la oficina de origen
        Assertions.assertThat(servicioUjapack.obtenerSituacion(envio.getKey()).getValue()).isEqualTo(util.Estado.EN_TRANSITO);  
    }
    
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testEnvioRutaCaso1() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
        Pair<String, Integer> envio = servicioUjapack.creaEnvio(5, 5, 5, cliente, cliente);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina + 2 entrada/salida del Repartidor ) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getKey()).getRuta().size()).isEqualTo(4);  
    }
    
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testEnvioRutaCaso2() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con origen y destino en distintas oficinas dentro del mismo centro
        Pair<String, Integer> envio = servicioUjapack.creaEnvio(5, 5, 5, origen, destino);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina origen + 2 entrada/salida del Centro Logistico +
         *   2 entrada/salida de la Oficina destino + 2 entrada/salida del Repartidor ) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getKey()).getRuta().size()).isEqualTo(8);  
    }
    
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testEnvioRutaCaso3() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Albacete");
        
        // Creación de envio con origen y destino en distintos centros
        Pair<String, Integer> envio = servicioUjapack.creaEnvio(5, 5, 5, origen, destino);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina + 
         *   N entrada/salida del Centro Logistico(4 -> 2 de Andalucía + 2 de La Mancha) +
         *   2 entrada/salida de la Oficina destino + 2 entrada/salida del Repartidor ) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getKey()).getRuta().size()).isEqualTo(10);  
    }
    
    @Test
    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    public void testActualizaEnvioInvalido() {        
        // Intento actualizar un envio que no existe
        Assertions.assertThatThrownBy(() -> {
            servicioUjapack.actualizar("1234567890", LocalDateTime.now(), true, "1"); }) // En id no existe
                .isInstanceOf(EnvioNoExiste.class);
    }
}
