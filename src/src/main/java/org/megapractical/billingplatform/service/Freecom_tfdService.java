package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_tfd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_tfd.
 */
public interface Freecom_tfdService {

    /**
     * Save a freecom_tfd.
     * 
     * @param freecom_tfd the entity to save
     * @return the persisted entity
     */
    Freecom_tfd save(Freecom_tfd freecom_tfd);

    /**
     *  Get all the freecom_tfds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_tfd> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_tfd.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_tfd findOne(Long id);

    /**
     *  Delete the "id" freecom_tfd.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
