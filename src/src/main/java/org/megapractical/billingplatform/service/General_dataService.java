package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.General_data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing General_data.
 */
public interface General_dataService {

    /**
     * Save a general_data.
     * 
     * @param general_data the entity to save
     * @return the persisted entity
     */
    General_data save(General_data general_data);

    /**
     *  Get all the general_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<General_data> findAll(Pageable pageable);

    /**
     *  Get the "id" general_data.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    General_data findOne(Long id);

    /**
     *  Delete the "id" general_data.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
