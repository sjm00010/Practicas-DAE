package dae.ujapack.repositorios;

import dae.ujapack.entidades.Envio;
import dae.ujapack.utils.Utils;
import java.time.LocalDateTime;
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
     * Función para listar envios que puedas estar extraviados. No incluye envios ya extraviados ni entregados.
     * @return Lista de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Envio> obtenerPosiblesExtraviados(){
        return em.createQuery("SELECT e FROM Envio e WHERE e.estado != :entregado AND e.estado != :extraviado AND e.fechaActualizado >= :fecha", Envio.class)
                    .setParameter("entregado", Utils.Estado.ENTREGADO)
                    .setParameter("extraviado", Utils.Estado.EXTRAVIADO)
                    .setParameter("fecha", LocalDateTime.now().minusDays(1))
                    .getResultList();
    }
    
    /**
     * Función que lista los envios extraviados entre dos fechas, si no se indican devuelve todos los envios extraviados.
     * @param inicio Fecha de inicio, OPCIONAL
     * @param fin Fecha de fin, OPCIONAL
     * @return Lista de envios extraviados
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Envio> buscaExtraviados(LocalDateTime inicio, LocalDateTime fin){
        inicio = inicio != null ? inicio : LocalDateTime.MIN;
        fin = fin != null ? fin : LocalDateTime.now();
        return em.createQuery("SELECT e FROM Envio e WHERE e.estado != :estado AND e.fechaActualizado BETWEEN :inicio AND :fin", Envio.class)
                    .setParameter("estado", Utils.Estado.EXTRAVIADO)
                    .setParameter("inicio", inicio)
                    .setParameter("fin", fin)
                    .getResultList();
    }
    
    /**
     * Función contar el numero de envios existentes
     * @param inicio Fecha a partir de la que se van a contar los envios
     * @return Numero de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long numEnvios(LocalDateTime inicio){
        return em.createQuery("SELECT COUNT(e) FROM Envio e WHERE e.fechaActualizado > :inicio", Long.class)
                .setParameter("inicio", inicio).getSingleResult();
    }
    
    /**
     * Función contar el numero de envios existentes
     * @param inicio Fecha a partir de la que se van a contar los envios
     * @return Numero de envíos
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public long numExtraviados(LocalDateTime inicio){
        return em.createQuery("SELECT COUNT(e) FROM Envio e WHERE e.estado != :estado AND e.fechaActualizado > :inicio", Long.class)
                .setParameter("estado", Utils.Estado.EXTRAVIADO)
                .setParameter("inicio", inicio).getSingleResult();
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
