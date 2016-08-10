package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_spei;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_spei.
 */
public interface Freecom_speiService {

    /**
     * Save a freecom_spei.
     * 
     * @param freecom_spei the entity to save
     * @return the persisted entity
     */
    Freecom_spei save(Freecom_spei freecom_spei);

    /**
     *  Get all the freecom_speis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_spei> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_spei.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_spei findOne(Long id);

    /**
     *  Delete the "id" freecom_spei.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
