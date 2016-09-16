package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_system;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_system.
 */
public interface C_systemService {

    /**
     * Save a c_system.
     * 
     * @param c_system the entity to save
     * @return the persisted entity
     */
    C_system save(C_system c_system);

    /**
     *  Get all the c_systems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_system> findAll(Pageable pageable);

    /**
     *  Get the "id" c_system.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_system findOne(Long id);

    /**
     *  Delete the "id" c_system.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
