package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_spei;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_spei.
 */
public interface Com_speiService {

    /**
     * Save a com_spei.
     * 
     * @param com_spei the entity to save
     * @return the persisted entity
     */
    Com_spei save(Com_spei com_spei);

    /**
     *  Get all the com_speis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_spei> findAll(Pageable pageable);

    /**
     *  Get the "id" com_spei.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_spei findOne(Long id);

    /**
     *  Delete the "id" com_spei.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
