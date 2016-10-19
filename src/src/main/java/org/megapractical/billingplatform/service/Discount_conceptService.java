package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Discount_concept;

import java.util.List;

/**
 * Service Interface for managing Discount_concept.
 */
public interface Discount_conceptService {

    /**
     * Save a discount_concept.
     *
     * @param discount_concept the entity to save
     * @return the persisted entity
     */
    Discount_concept save(Discount_concept discount_concept);

    /**
     *  Get all the discount_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    List<Discount_concept> findAll(Long taxpayerconcept);

    /**
     *  Get the "id" discount_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Discount_concept findOne(Long id);

    /**
     *  Delete the "id" discount_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
