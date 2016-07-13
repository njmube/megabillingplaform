package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_pfic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_pfic.
 */
public interface Freecom_pficService {

    /**
     * Save a freecom_pfic.
     * 
     * @param freecom_pfic the entity to save
     * @return the persisted entity
     */
    Freecom_pfic save(Freecom_pfic freecom_pfic);

    /**
     *  Get all the freecom_pfics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_pfic> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_pfic.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_pfic findOne(Long id);

    /**
     *  Delete the "id" freecom_pfic.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
