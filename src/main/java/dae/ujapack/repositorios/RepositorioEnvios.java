package dae.ujapack.repositorios;

import dae.ujapack.entidades.Envio;
import dae.ujapack.utils.Utils;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(value="envios", key="#id")
    public Optional<Envio> buscar(String id){
        return Optional.ofNullable(em.find(Envio.class, id));
    }
    
    // No cacheo los metodos de buscar todos ya que cargar todos los envios en la cache sería ineficiente
    
    /**
     * Función para listar todos los envios no entregados.
     * @return Lista de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Envio> buscarNoEntregados(){
        return em.createQuery("SELECT e FROM Envio e WHERE e.estado != :estado", Envio.class).setParameter("estado", Utils.Estado.ENTREGADO).getResultList();
    }
    
    /**
     * Función para listar todos los envios no entregados.
     * @return Lista de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Envio> buscarTodos(){
        return em.createQuery("SELECT e FROM Envio e", Envio.class).getResultList();
    }
    
    /**
     * Función que crea un nuevo Envio
     * @param envio Envío a crear
     */
    @CacheEvict(value="envios" , key="#envio.getId()") // Es necesario invalidar el envio, ya que para generar el id del mismo uso el metodo de buscar
    public void crear(Envio envio){
        em.persist(envio);
    }
    
    /**
     * Función para actualizar los datos de un envío
     * @param envio Nuevo envio que actualizar
     */
    @CacheEvict(value="envios" , key="#envio.getId()")
    public void actualizaEnvio(Envio envio){
        em.merge(envio);
    }
}
