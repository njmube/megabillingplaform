package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_accreditation_ieps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_accreditation_ieps.
 */
public interface Freecom_accreditation_iepsService {

    /**
     * Save a freecom_accreditation_ieps.
     * 
     * @param freecom_accreditation_ieps the entity to save
     * @return the persisted entity
     */
    Freecom_accreditation_ieps save(Freecom_accreditation_ieps freecom_accreditation_ieps);

    /**
     *  Get all the freecom_accreditation_ieps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_accreditation_ieps> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_accreditation_ieps.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_accreditation_ieps findOne(Long id);

    /**
     *  Delete the "id" freecom_accreditation_ieps.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
