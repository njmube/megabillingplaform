package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_educational_institutions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_educational_institutions.
 */
public interface Freecom_educational_institutionsService {

    /**
     * Save a freecom_educational_institutions.
     * 
     * @param freecom_educational_institutions the entity to save
     * @return the persisted entity
     */
    Freecom_educational_institutions save(Freecom_educational_institutions freecom_educational_institutions);

    /**
     *  Get all the freecom_educational_institutions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_educational_institutions> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_educational_institutions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_educational_institutions findOne(Long id);

    /**
     *  Delete the "id" freecom_educational_institutions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
