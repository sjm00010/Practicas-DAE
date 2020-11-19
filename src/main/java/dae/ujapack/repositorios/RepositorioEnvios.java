package dae.ujapack.repositorios;

import dae.ujapack.entidades.Envio;
import dae.ujapack.entidades.Paso;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de Envio
 * @author sjm00010
 */
@Repository
@Transactional
public class RepositorioEnvios {
    @PersistenceContext
    EntityManager em;
    
    /**
     * Función para buscar un envío por su id.
     * @param id ID del envío
     * @return Envío
     */
    public Optional<Envio> buscar(String id){
        return Optional.ofNullable(em.find(Envio.class, id));
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
    
    // Este repositorio tambien se encargar de gestionar el Paso
    /**
     * Función que crea un nuevo Paso
     * @param envio Paso a crear
     */
    public void creaPaso(Paso paso){
        em.persist(paso);
    }
    
    /**
     * Función para actualizar los datos de un envío
     * @param envio Nuevo envio que actualizar
     */
    public void actualizaPaso(Paso paso){
        em.merge(paso);
    }
}
