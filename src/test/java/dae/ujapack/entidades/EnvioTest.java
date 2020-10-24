package dae.ujapack.entidades;

import dae.ujapack.errores.IdPuntoControlInvalido;
import dae.ujapack.objetosvalor.Cliente;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    }
    
    @Test
    void testActualizarInvalido(){
        Cliente origen = new Cliente("111111111A", "OtraPrueba", "Pruebas", "Almería");
        Cliente destino = new Cliente("111111111A", "OtraPrueba", "Pruebas", "Almería");
        ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("2");
        CentroLogistico centro = new CentroLogistico("1", "CL", "Andalucia-Extremadura", conexiones);
        CentroLogistico centro1 = new CentroLogistico("2", "CL", "Castilla La Mancha", conexiones);
        
        ArrayList<Paso> ruta = new ArrayList<>();
        ruta.add(new Paso(centro, true));
        ruta.add(new Paso(centro1, true));

        Envio envio = new Envio("2437843955", 2, 2, 2, origen, destino, ruta);
        
        /*Comprobar que el el centro existe a través de la comprobación
          de la existencia de su ID. En caso de que no exista se lanza 
          excepción del tipo IdPuntoControlInvalido */
        assertThatThrownBy(() -> {envio.actualizar(LocalDate.now(), true, null); })
                .isInstanceOf(IdPuntoControlInvalido.class);
        }
    
    


    @Test
    void testCompruebaUltimoPC() throws IdPuntoControlInvalido{
        Cliente origen = new Cliente("222222222A", "OtraPrueba", "Pruebas", "Almería");
        Cliente destino = new Cliente("222222222A", "OtraPrueba", "Pruebas", "Almería");
        ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("2");
        CentroLogistico centro = new CentroLogistico("1", "CL", "Andalucia-Extremadura", conexiones);
        CentroLogistico centro2 = new CentroLogistico("2", "CL", "Castilla La Mancha", conexiones);
        ArrayList<Paso> ruta = new ArrayList<>();
        ruta.add(new Paso(centro, true));
        ruta.add(new Paso(centro2, true));
        
        Envio envio = new Envio("1234567890", 5, 5, 5, origen, destino, ruta);
    
        // Comprobar la obtención del último pc. Para ello primero actualiza y luego trata de obtenerlo.
        envio.actualizar(LocalDate.now(), true, centro);
        Assertions.assertThat(envio.getUltimoPunto()).isEqualTo(ruta.get(0));
}
    
}
    // TO DO : Comprobar actualizar centro sin actualizar anteriores (Crear error)
 

              


















