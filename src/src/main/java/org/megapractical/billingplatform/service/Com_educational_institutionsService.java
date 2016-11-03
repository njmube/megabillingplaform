package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_educational_institutions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_educational_institutions.
 */
public interface Com_educational_institutionsService {

    /**
     * Save a com_educational_institutions.
     * 
     * @param com_educational_institutions the entity to save
     * @return the persisted entity
     */
    Com_educational_institutions save(Com_educational_institutions com_educational_institutions);

    /**
     *  Get all the com_educational_institutions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_educational_institutions> findAll(Pageable pageable);

    /**
     *  Get the "id" com_educational_institutions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_educational_institutions findOne(Long id);

    /**
     *  Delete the "id" com_educational_institutions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
