package dae.ujapack.servicios;

import dae.ujapack.errores.EnvioNoExiste;
import dae.ujapack.objetosvalor.Cliente;
import dae.ujapack.utils.tuplas.LocalizadorPrecioEnvio;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import dae.ujapack.utils.Utils;
import java.time.LocalDateTime;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

/**
 * Test para ServicioMensajeria
 * @author sjm00010
 */
@SpringBootTest(classes = dae.ujapack.app.UjaPackApp.class)
public class ServicioMensajeriaTest {
        
    @Autowired
    ServicioMensajeria servicioUjapack;
    
    @Autowired
    ServicioLimpiadoBaseDatos limpiadorBaseDatos;

    @Test
    public void testAccesoServicioUjapack() {
        Assertions.assertThat(servicioUjapack).isNotNull();
    }
    
    @Test
    public void testCreaEnvioInvalido() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");        
        
        Assertions.assertThatThrownBy(() -> {
            servicioUjapack.creaEnvio(5, 5, 5, cliente, null); }) // No tiene destino, es nulo.
                .isInstanceOf(ConstraintViolationException.class);
    }
    
    @Test
    public void testGetSituacionEnvio() {
        
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        LocalizadorPrecioEnvio envio = servicioUjapack.creaEnvio(5, 5, 5, cliente, cliente);
        servicioUjapack.actualizar(envio.getIdentificador(), LocalDateTime.now(), true, "Almería");
                
        // Compruebo que esta en reparto, dado que sigue la oficina de origen
        Assertions.assertThat(servicioUjapack.obtenerSituacion(envio.getIdentificador()).getEstado()).isEqualTo(Utils.Estado.EN_REPARTO);  
    }
    
    @Test
    public void testEnvioRutaCaso1() {
        Cliente cliente = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con Origen y destino en la misma Oficina(Provincia)
        LocalizadorPrecioEnvio envio = servicioUjapack.creaEnvio(5, 5, 5, cliente, cliente);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina ) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getIdentificador()).getRuta().size()).isEqualTo(2);  
    }
    
    @Test
    public void testEnvioRutaCaso2() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        
        // Creación de envio con origen y destino en distintas oficinas dentro del mismo centro
        LocalizadorPrecioEnvio envio = servicioUjapack.creaEnvio(5, 5, 5, origen, destino);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina origen + 2 entrada/salida del Centro Logistico +
         *   2 entrada/salida de la Oficina destino ) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getIdentificador()).getRuta().size()).isEqualTo(6);  
    }
    
    @Test
    public void testEnvioRutaCaso3() {
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Jaén");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Albacete");
        
        // Creación de envio con origen y destino en distintos centros
        LocalizadorPrecioEnvio envio = servicioUjapack.creaEnvio(5, 5, 5, origen, destino);
        
        /** Compruebo que la ruta generada contiene los puntos que deberia
         * ( 2 entrada/salida de la Oficina + 
         *   N entrada/salida del Centro Logistico(4 -> 2 de Andalucía + 2 de La Mancha) +
         *   2 entrada/salida de la Oficina destino) */
        Assertions.assertThat(servicioUjapack.getEnvio(envio.getIdentificador()).getRuta().size()).isEqualTo(8);  
    }
    
    @Test
    public void testActualizaEnvioInvalido() {        
        // Intento actualizar un envio que no existe
        Assertions.assertThatThrownBy(() -> {
            servicioUjapack.actualizar("1234567890", LocalDateTime.now(), true, "1"); }) // En id no existe
                .isInstanceOf(EnvioNoExiste.class);
    }
    
    @BeforeEach
    void limpiarBaseDatos() {
        limpiadorBaseDatos.limpiar();
    }

}
