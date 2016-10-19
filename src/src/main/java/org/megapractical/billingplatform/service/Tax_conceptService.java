package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service Interface for managing Tax_concept.
 */
public interface Tax_conceptService {

    /**
     * Save a tax_concept.
     *
     * @param tax_concept the entity to save
     * @return the persisted entity
     */
    Tax_concept save(Tax_concept tax_concept);

    /**
     *  Get all the tax_concepts.
     *
     *  @param pageable the pagination information
     *  @param taxpayeraccount
     * @param tax_type
     *@param rate
     * @param concept @return the list of entities
     */
    Page<Tax_concept> findAll(Pageable pageable, Integer taxpayeraccount, String tax_type, BigDecimal rate, String concept);

    /**
     *  Get the "id" tax_concept.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_concept findOne(Long id);

    /**
     *  Delete the "id" tax_concept.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
