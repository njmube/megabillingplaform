package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_part_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_part_concept.
 */
public interface Free_part_conceptService {

    /**
     * Save a free_part_concept.
     * 
     * @param free_part_concept the entity to save
     * @return the persisted entity
     */
    Free_part_concept save(Free_part_concept free_part_concept);

    /**
     *  Get all the free_part_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_part_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" free_part_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_part_concept findOne(Long id);

    /**
     *  Delete the "id" free_part_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
