/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.repositorios;

import dae.ujapack.entidades.puntosControl.CentroLogistico;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    public Optional<CentroLogistico> buscar(String id){
        return Optional.ofNullable(em.find(CentroLogistico.class, id));
    }
    
    /**
     * Función que se encarga de crear un nuevo CentroLogistico
     * @param centro Centro logístico a crear
     */
    public void guardar(CentroLogistico centro) {
        em.persist(centro);
    }   
   
}
