package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Well_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Well_type.
 */
public interface Well_typeService {

    /**
     * Save a well_type.
     * 
     * @param well_type the entity to save
     * @return the persisted entity
     */
    Well_type save(Well_type well_type);

    /**
     *  Get all the well_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Well_type> findAll(Pageable pageable);

    /**
     *  Get the "id" well_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Well_type findOne(Long id);

    /**
     *  Delete the "id" well_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
