package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_retentions_transfered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_retentions_transfered.
 */
public interface Freecom_retentions_transferedService {

    /**
     * Save a freecom_retentions_transfered.
     * 
     * @param freecom_retentions_transfered the entity to save
     * @return the persisted entity
     */
    Freecom_retentions_transfered save(Freecom_retentions_transfered freecom_retentions_transfered);

    /**
     *  Get all the freecom_retentions_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_retentions_transfered> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_retentions_transfered.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_retentions_transfered findOne(Long id);

    /**
     *  Delete the "id" freecom_retentions_transfered.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
