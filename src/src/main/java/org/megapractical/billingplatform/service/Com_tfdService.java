package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_tfd;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_tfd.
 */
public interface Com_tfdService {

    /**
     * Save a com_tfd.
     * 
     * @param com_tfd the entity to save
     * @return the persisted entity
     */
    Com_tfd save(Com_tfd com_tfd);

    /**
     *  Get all the com_tfds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_tfd> findAll(Pageable pageable);

    /**
     *  Get the "id" com_tfd.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_tfd findOne(Long id);

    /**
     *  Delete the "id" com_tfd.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
