package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_concept_fuel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_concept_fuel.
 */
public interface Com_concept_fuelService {

    /**
     * Save a com_concept_fuel.
     * 
     * @param com_concept_fuel the entity to save
     * @return the persisted entity
     */
    Com_concept_fuel save(Com_concept_fuel com_concept_fuel);

    /**
     *  Get all the com_concept_fuels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_concept_fuel> findAll(Pageable pageable);

    /**
     *  Get the "id" com_concept_fuel.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_concept_fuel findOne(Long id);

    /**
     *  Delete the "id" com_concept_fuel.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
