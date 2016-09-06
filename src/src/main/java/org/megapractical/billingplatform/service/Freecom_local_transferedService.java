package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_local_transfered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_local_transfered.
 */
public interface Freecom_local_transferedService {

    /**
     * Save a freecom_local_transfered.
     * 
     * @param freecom_local_transfered the entity to save
     * @return the persisted entity
     */
    Freecom_local_transfered save(Freecom_local_transfered freecom_local_transfered);

    /**
     *  Get all the freecom_local_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_local_transfered> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_local_transfered.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_local_transfered findOne(Long id);

    /**
     *  Delete the "id" freecom_local_transfered.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
