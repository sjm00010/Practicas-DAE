package dae.ujapack.repositorios;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import dae.ujapack.entidades.puntosControl.PuntoControl;
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
 * Repositorios de los puntos de control
 *
 * @author sjm00010
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioPuntoControl {

    @PersistenceContext
    EntityManager em;

    /**
     * Función para buscar una oficina a través de su id.
     *
     * @param id ID de la oficina
     * @return Oficina
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "puntoscontrol", key = "#id")
    public Optional<PuntoControl> buscar(String id) {
        return Optional.ofNullable(em.find(PuntoControl.class, id));
    }

    /**
     * Función que crea los puntos de control de la colección.
     *
     * @param puntos Colección de puntos de control a guardar
     */
    public void guardar(Collection<? extends PuntoControl> puntos) {
        puntos.forEach(punto -> em.persist(punto));
    }

    /**
     * Función para buscar todos los centros logísticos
     *
     * @return Devuelve una lista con los centros logísticos o una lista vacía
     * si no ha encontrado ningún centro logístico.
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Cacheable(value = "puntoscontrol")
    public List<CentroLogistico> obtenCentros() {
        List<CentroLogistico> centros = em.createQuery("SELECT c FROM CentroLogistico c JOIN FETCH c.conexiones", CentroLogistico.class).getResultList();
        return centros;
    }

    /**
     * Función para comprobar si hay centros logisticos en la base de datos
     *
     * @return True si hay datos, false si no
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public boolean isEmpty() {
        return em.createQuery("SELECT c FROM CentroLogistico c", CentroLogistico.class).setMaxResults(1).getResultList().isEmpty();
    }
}
