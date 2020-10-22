/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.entidades;

import dae.ujapack.errores.IdPuntoControlInvalido;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.validation.ConstraintViolationException;
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
        ArrayList<String> conexiones = new ArrayList<>();
        conexiones.add("2");
        //Comprobar que un centro logístico está conectado, es decir, tiene alguna conexión con otro centro logístico.
        //En vez de pasarle las conexiones que tiene, le paso null así se lanzará una excepción.
        CentroLogistico centroLogistico = new CentroLogistico("0", "Toulousse", "Francia", null);
        assertThatThrownBy(() -> { centroLogistico.getConexiones().isEmpty(); })
                .isInstanceOf(NullPointerException.class);
        }
    }
     

