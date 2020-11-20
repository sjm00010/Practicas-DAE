package dae.ujapack.servicios;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;


/**
 *
 * @author sjm00010
 */
@Service
public class ServicioLimpiadoBaseDatos {
    @PersistenceContext
    EntityManager em;
    
    @Autowired
    TransactionTemplate transactionTemplate;

    /** 
     * Lista de entidades a borrar. Ojo: el orden es muy importante
     * para evitar errores de violación de integridad 
     */
    final String[] entidades = {
        "Envio", 
        "Paso"         
    };
    
    final String deleteFrom = "delete from ";
    
    /** Realizar borrado */
    void limpiar() {
        transactionTemplate.executeWithoutResult(transactionStatus -> {
            for (String tabla : entidades) {
                em.createQuery(deleteFrom + tabla).executeUpdate();
            }
        });
    }
}

