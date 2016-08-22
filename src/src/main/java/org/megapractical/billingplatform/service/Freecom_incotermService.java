package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_incoterm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_incoterm.
 */
public interface Freecom_incotermService {

    /**
     * Save a freecom_incoterm.
     * 
     * @param freecom_incoterm the entity to save
     * @return the persisted entity
     */
    Freecom_incoterm save(Freecom_incoterm freecom_incoterm);

    /**
     *  Get all the freecom_incoterms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_incoterm> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_incoterm.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_incoterm findOne(Long id);

    /**
     *  Delete the "id" freecom_incoterm.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
