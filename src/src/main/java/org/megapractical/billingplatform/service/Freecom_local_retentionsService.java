package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_local_retentions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_local_retentions.
 */
public interface Freecom_local_retentionsService {

    /**
     * Save a freecom_local_retentions.
     * 
     * @param freecom_local_retentions the entity to save
     * @return the persisted entity
     */
    Freecom_local_retentions save(Freecom_local_retentions freecom_local_retentions);

    /**
     *  Get all the freecom_local_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_local_retentions> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_local_retentions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_local_retentions findOne(Long id);

    /**
     *  Delete the "id" freecom_local_retentions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
