package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_transit_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_transit_type.
 */
public interface C_transit_typeService {

    /**
     * Save a c_transit_type.
     * 
     * @param c_transit_type the entity to save
     * @return the persisted entity
     */
    C_transit_type save(C_transit_type c_transit_type);

    /**
     *  Get all the c_transit_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_transit_type> findAll(Pageable pageable);

    /**
     *  Get the "id" c_transit_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_transit_type findOne(Long id);

    /**
     *  Delete the "id" c_transit_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
