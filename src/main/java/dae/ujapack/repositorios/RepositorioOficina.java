/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dae.ujapack.repositorios;

import dae.ujapack.entidades.puntosControl.Oficina;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repositorio de Oficina
 * @author juanc
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class RepositorioOficina {
    @PersistenceContext
    EntityManager em;
    
    /**
     * Función para buscar una oficina a través de su id.
     * @param id ID de la oficina
     * @return Oficina
     */
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Oficina> buscar(String id){
        return Optional.ofNullable(em.find(Oficina.class, id));
    }
    
    /**
     * Función que se encarga de crear una nueva Oficina
     * @param oficina Oficina a crear
     */
    public void guardar(Oficina oficina){
        em.persist(oficina);
    }
}
