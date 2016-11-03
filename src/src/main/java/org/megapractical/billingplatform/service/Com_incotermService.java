package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_incoterm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_incoterm.
 */
public interface Com_incotermService {

    /**
     * Save a com_incoterm.
     * 
     * @param com_incoterm the entity to save
     * @return the persisted entity
     */
    Com_incoterm save(Com_incoterm com_incoterm);

    /**
     *  Get all the com_incoterms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_incoterm> findAll(Pageable pageable);

    /**
     *  Get the "id" com_incoterm.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_incoterm findOne(Long id);

    /**
     *  Delete the "id" com_incoterm.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
