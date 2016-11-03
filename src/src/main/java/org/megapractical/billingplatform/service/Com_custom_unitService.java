package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_custom_unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_custom_unit.
 */
public interface Com_custom_unitService {

    /**
     * Save a com_custom_unit.
     * 
     * @param com_custom_unit the entity to save
     * @return the persisted entity
     */
    Com_custom_unit save(Com_custom_unit com_custom_unit);

    /**
     *  Get all the com_custom_units.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_custom_unit> findAll(Pageable pageable);

    /**
     *  Get the "id" com_custom_unit.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_custom_unit findOne(Long id);

    /**
     *  Delete the "id" com_custom_unit.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
