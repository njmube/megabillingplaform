package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_ecc_11_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_ecc_11_concept.
 */
public interface Com_ecc_11_conceptService {

    /**
     * Save a com_ecc_11_concept.
     * 
     * @param com_ecc_11_concept the entity to save
     * @return the persisted entity
     */
    Com_ecc_11_concept save(Com_ecc_11_concept com_ecc_11_concept);

    /**
     *  Get all the com_ecc_11_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_ecc_11_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" com_ecc_11_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_ecc_11_concept findOne(Long id);

    /**
     *  Delete the "id" com_ecc_11_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
