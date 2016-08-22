package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_custom_unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_custom_unit.
 */
public interface Freecom_custom_unitService {

    /**
     * Save a freecom_custom_unit.
     * 
     * @param freecom_custom_unit the entity to save
     * @return the persisted entity
     */
    Freecom_custom_unit save(Freecom_custom_unit freecom_custom_unit);

    /**
     *  Get all the freecom_custom_units.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_custom_unit> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_custom_unit.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_custom_unit findOne(Long id);

    /**
     *  Delete the "id" freecom_custom_unit.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
