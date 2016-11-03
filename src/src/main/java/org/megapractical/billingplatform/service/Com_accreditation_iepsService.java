package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_accreditation_ieps;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_accreditation_ieps.
 */
public interface Com_accreditation_iepsService {

    /**
     * Save a com_accreditation_ieps.
     * 
     * @param com_accreditation_ieps the entity to save
     * @return the persisted entity
     */
    Com_accreditation_ieps save(Com_accreditation_ieps com_accreditation_ieps);

    /**
     *  Get all the com_accreditation_ieps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_accreditation_ieps> findAll(Pageable pageable);

    /**
     *  Get the "id" com_accreditation_ieps.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_accreditation_ieps findOne(Long id);

    /**
     *  Delete the "id" com_accreditation_ieps.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
