package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_pfic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_pfic.
 */
public interface Com_pficService {

    /**
     * Save a com_pfic.
     * 
     * @param com_pfic the entity to save
     * @return the persisted entity
     */
    Com_pfic save(Com_pfic com_pfic);

    /**
     *  Get all the com_pfics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_pfic> findAll(Pageable pageable);

    /**
     *  Get the "id" com_pfic.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_pfic findOne(Long id);

    /**
     *  Delete the "id" com_pfic.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
