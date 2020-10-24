package dae.ujapack.entidades;

import dae.ujapack.objetosvalor.Cliente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test para clase Envio
 * @author sjm00010
 */
public class EnvioTest {

    public EnvioTest() {
    }
    
    @Test
    void testValidaEnvio(){
        Cliente origen = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        Cliente destino = new Cliente("11111111A", "Prueba", "Pruebas", "Almería");
        ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("2");
        CentroLogistico centro = new CentroLogistico("1", "CL", "Andalucia", conexiones);
        CentroLogistico centro2 = new CentroLogistico("2", "CL", "Extremadiura", conexiones);
        ArrayList<Paso> ruta = new ArrayList<>();
        ruta.add(new Paso(centro, true));
        ruta.add(new Paso(centro2, true));
        
        Envio envio = new Envio("1234567890", 5, 5, 5, origen, destino, ruta);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Envio>> violations = validator.validate(envio);

        Assertions.assertThat(violations).isEmpty(); // Compruebo que se ha creado correctamente
        Assertions.assertThat(envio.getPrecio()).isEqualTo(5*5*5*2/1000); // Compruebo que se calcula bien el precio
    
        // Comprobar la obtencion del ultimo pc. Para ello primero actualiza y luego trata de obtenerlo
        envio.actualizar(LocalDate.now(), true, centro);
        Assertions.assertThat(envio.getUltimoPunto()).isEqualTo(ruta.get(0));
    }
    
    // TO DO : Comprobar actualizar centro no existente (Crear error)
    
    // TO DO : Comprobar actualizar centro sin actualizar anteriores (Crear error)
    
}
