package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service Interface for managing Taxpayer_concept.
 */
public interface Taxpayer_conceptService {

    /**
     * Save a taxpayer_concept.
     *
     * @param taxpayer_concept the entity to save
     * @return the persisted entity
     */
    Taxpayer_concept save(Taxpayer_concept taxpayer_concept);

    /**
     *  Get all the taxpayer_concepts.
     *
     *  @param pageable the pagination information
     *  @param taxpayeraccount
     *@param no_identification
     * @param description
     * @param measure_unit
     * @param unit_value @return the list of entities
     */
    Page<Taxpayer_concept> findAll(Pageable pageable, Integer taxpayeraccount, String no_identification, String description, String measure_unit, BigDecimal unit_value);

    /**
     *  Get the "id" taxpayer_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_concept findOne(Long id);

    /**
     *  Delete the "id" taxpayer_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
