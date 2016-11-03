package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_donees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_donees.
 */
public interface Com_doneesService {

    /**
     * Save a com_donees.
     * 
     * @param com_donees the entity to save
     * @return the persisted entity
     */
    Com_donees save(Com_donees com_donees);

    /**
     *  Get all the com_donees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_donees> findAll(Pageable pageable);

    /**
     *  Get the "id" com_donees.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_donees findOne(Long id);

    /**
     *  Delete the "id" com_donees.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
