package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_paybill_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_paybill_concept.
 */
public interface Freecom_paybill_conceptService {

    /**
     * Save a freecom_paybill_concept.
     * 
     * @param freecom_paybill_concept the entity to save
     * @return the persisted entity
     */
    Freecom_paybill_concept save(Freecom_paybill_concept freecom_paybill_concept);

    /**
     *  Get all the freecom_paybill_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_paybill_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_paybill_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_paybill_concept findOne(Long id);

    /**
     *  Delete the "id" freecom_paybill_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
