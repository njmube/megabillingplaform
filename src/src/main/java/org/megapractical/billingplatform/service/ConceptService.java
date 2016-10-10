package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service Interface for managing Concept.
 */
public interface ConceptService {

    /**
     * Save a concept.
     *
     * @param concept the entity to save
     * @return the persisted entity
     */
    Concept save(Concept concept);

    /**
     *  Get all the concepts.
     *
     *  @param pageable the pagination information
     *  @param taxpayeraccount
     *@param no_identification
     * @param description
     * @param measure_unit
     * @param unit_value @return the list of entities
     */
    Page<Concept> findAll(Pageable pageable, Integer taxpayeraccount, String no_identification, String description, String measure_unit, BigDecimal unit_value);

    /**
     *  Get the "id" concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Concept findOne(Long id);

    /**
     *  Delete the "id" concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
