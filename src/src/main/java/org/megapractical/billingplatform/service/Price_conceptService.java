package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Price_concept;

import java.util.List;

/**
 * Service Interface for managing Price_concept.
 */
public interface Price_conceptService {

    /**
     * Save a price_concept.
     *
     * @param price_concept the entity to save
     * @return the persisted entity
     */
    Price_concept save(Price_concept price_concept);

    /**
     *  Get all the price_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    List<Price_concept> findAll(Long taxpayerconcept);

    /**
     *  Get the "id" price_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Price_concept findOne(Long id);

    /**
     *  Delete the "id" price_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
