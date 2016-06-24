package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_concept.
 */
public interface Free_conceptService {

    /**
     * Save a free_concept.
     * 
     * @param free_concept the entity to save
     * @return the persisted entity
     */
    Free_concept save(Free_concept free_concept);

    /**
     *  Get all the free_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" free_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_concept findOne(Long id);

    /**
     *  Delete the "id" free_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
