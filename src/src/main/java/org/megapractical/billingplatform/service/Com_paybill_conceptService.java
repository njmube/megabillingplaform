package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_paybill_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_paybill_concept.
 */
public interface Com_paybill_conceptService {

    /**
     * Save a com_paybill_concept.
     * 
     * @param com_paybill_concept the entity to save
     * @return the persisted entity
     */
    Com_paybill_concept save(Com_paybill_concept com_paybill_concept);

    /**
     *  Get all the com_paybill_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_paybill_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" com_paybill_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_paybill_concept findOne(Long id);

    /**
     *  Delete the "id" com_paybill_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
