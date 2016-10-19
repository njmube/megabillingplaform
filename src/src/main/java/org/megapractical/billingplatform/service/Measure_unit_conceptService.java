package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Measure_unit_concept;

import java.util.List;

/**
 * Service Interface for managing Measure_unit_concept.
 */
public interface Measure_unit_conceptService {

    /**
     * Save a measure_unit_concept.
     *
     * @param measure_unit_concept the entity to save
     * @return the persisted entity
     */
    Measure_unit_concept save(Measure_unit_concept measure_unit_concept);

    /**
     *  Get all the measure_unit_concepts.
     *
     *  @return the list of entities
     * @param taxpayerconcept
     */
    List<Measure_unit_concept> findAll(Long taxpayerconcept);

    /**
     *  Get the "id" measure_unit_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Measure_unit_concept findOne(Long id);

    /**
     *  Delete the "id" measure_unit_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
