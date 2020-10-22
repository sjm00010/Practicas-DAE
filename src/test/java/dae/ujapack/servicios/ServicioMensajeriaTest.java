package dae.ujapack.servicios;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Test para ServicioMensajeria
 * @author sjm00010
 */
public class ServicioMensajeriaTest {
        
    @Autowired
    ServicioMensajeria servicioUjapack;

    @Test
    public void testAccesoServicioUjaCoin() {
        Assertions.assertThat(servicioUjapack).isNotNull();
    }

    
}
