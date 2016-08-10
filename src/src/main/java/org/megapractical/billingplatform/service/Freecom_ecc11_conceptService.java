package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_ecc11_concept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_ecc11_concept.
 */
public interface Freecom_ecc11_conceptService {

    /**
     * Save a freecom_ecc11_concept.
     * 
     * @param freecom_ecc11_concept the entity to save
     * @return the persisted entity
     */
    Freecom_ecc11_concept save(Freecom_ecc11_concept freecom_ecc11_concept);

    /**
     *  Get all the freecom_ecc11_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_ecc11_concept> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_ecc11_concept.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_ecc11_concept findOne(Long id);

    /**
     *  Delete the "id" freecom_ecc11_concept.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
