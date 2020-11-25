package dae.ujapack.repositorios;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de CentroLogistico
 * @author juanc
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioCentroLogistico {
    @PersistenceContext
    EntityManager em;
    
    /**
     * Función para buscar un centro logístico a través de su id.
     * @param id ID del centro logístico
     * @return CentroLogistico
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "puntoscontrol", key = "#id")
    public Optional<CentroLogistico> buscar(String id){
        return Optional.ofNullable(em.find(CentroLogistico.class, id));
    }
    
    /**
     * Función que se encarga de crear los CentroLogisticos
     * @param centros Centro logístico a crear
     */
    public void guardar(Collection<CentroLogistico> centros){
        centros.forEach(centro -> em.persist(centro));
    }
   
   /**
    * Función para buscar todos los centros logísticos
    * @return Devuelve una lista con los centros logísticos o una lista vacía si no ha encontrado ningún centro logístico.
    */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "puntoscontrol")
    public List<CentroLogistico> buscarTodos(){
        List<CentroLogistico> centros = em.createQuery("SELECT c FROM CentroLogistico c JOIN FETCH c.conexiones", CentroLogistico.class).getResultList();
        return centros;
    }
    
    /**
     * Función para comprobar si hay centros logisticos en la base de datos
     * @return True si hay datos, false si no
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean isEmpty() {
        return em.createQuery("SELECT c FROM CentroLogistico c", CentroLogistico.class).setMaxResults(1).getResultList().isEmpty();
    }
}
