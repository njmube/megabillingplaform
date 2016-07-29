package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_type_road;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_type_road.
 */
public interface C_type_roadService {

    /**
     * Save a c_type_road.
     * 
     * @param c_type_road the entity to save
     * @return the persisted entity
     */
    C_type_road save(C_type_road c_type_road);

    /**
     *  Get all the c_type_roads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_type_road> findAll(Pageable pageable);

    /**
     *  Get the "id" c_type_road.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_type_road findOne(Long id);

    /**
     *  Delete the "id" c_type_road.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
