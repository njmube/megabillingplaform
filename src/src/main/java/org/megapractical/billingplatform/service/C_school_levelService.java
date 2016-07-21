package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_school_level;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_school_level.
 */
public interface C_school_levelService {

    /**
     * Save a c_school_level.
     * 
     * @param c_school_level the entity to save
     * @return the persisted entity
     */
    C_school_level save(C_school_level c_school_level);

    /**
     *  Get all the c_school_levels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_school_level> findAll(Pageable pageable);

    /**
     *  Get the "id" c_school_level.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_school_level findOne(Long id);

    /**
     *  Delete the "id" c_school_level.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
