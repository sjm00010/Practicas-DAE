package dae.ujapack.entidades;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        CentroLogistico centroLogistico = new CentroLogistico("0", "Toulousse", "Francia", null);
        assertThatThrownBy(() -> { centroLogistico.getConexiones().isEmpty(); })
                .isInstanceOf(NullPointerException.class);
        }
    }
     

