package dae.ujapack.repositorios;

import dae.ujapack.entidades.Envio;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de Envio
 * @author sjm00010
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioEnvios {
    @PersistenceContext
    EntityManager em;
    
    /**
     * Función para buscar un envío por su id.
     * @param id ID del envío
     * @return Envío
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Envio> buscar(String id){
        return Optional.ofNullable(em.find(Envio.class, id));
    }
    
    /**
     * Función para listar todos los envios.
     * @return Lista de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Envio> buscarTodos(){
        return em.createQuery("SELECT e FROM Envio e JOIN FETCH e.ruta", Envio.class).getResultList();
    }
    
    /**
     * Función que crea un nuevo Envio
     * @param envio Envío a crear
     */
    public void crear(Envio envio){
        em.persist(envio);
    }
    
    /**
     * Función para actualizar los datos de un envío
     * @param envio Nuevo envio que actualizar
     */
    public void actualizaEnvio(Envio envio){
        em.merge(envio);
    }
}
