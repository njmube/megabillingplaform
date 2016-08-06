package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_concept_fuel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_concept_fuel.
 */
public interface Freecom_concept_fuelService {

    /**
     * Save a freecom_concept_fuel.
     * 
     * @param freecom_concept_fuel the entity to save
     * @return the persisted entity
     */
    Freecom_concept_fuel save(Freecom_concept_fuel freecom_concept_fuel);

    /**
     *  Get all the freecom_concept_fuels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_concept_fuel> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_concept_fuel.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_concept_fuel findOne(Long id);

    /**
     *  Delete the "id" freecom_concept_fuel.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
