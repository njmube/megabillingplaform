package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_well_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_well_type.
 */
public interface C_well_typeService {

    /**
     * Save a c_well_type.
     * 
     * @param c_well_type the entity to save
     * @return the persisted entity
     */
    C_well_type save(C_well_type c_well_type);

    /**
     *  Get all the c_well_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_well_type> findAll(Pageable pageable);

    /**
     *  Get the "id" c_well_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_well_type findOne(Long id);

    /**
     *  Delete the "id" c_well_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
