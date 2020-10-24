package dae.ujapack.entidades;

import java.util.ArrayList;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author juanc
 */
public class CentroLogisticoTest {
    
     public CentroLogisticoTest() {
    }
     
    @Test
    void testCreaCentroLogistico(){
        //Comprueba que un centro logístico está conectado, es decir, tiene alguna conexión con otro centro logístico.
        ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("2");
        CentroLogistico centroLogistico = new CentroLogistico("1", "CL", "Andalucia-Extremadura", conexiones);
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<CentroLogistico>> violations = validator.validate(centroLogistico);
        Assertions.assertThat(violations).isEmpty(); // Compruebo que se ha creado correctamente      
        }
    }
     

