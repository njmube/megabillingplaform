package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_local_transfered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_local_transfered.
 */
public interface Com_local_transferedService {

    /**
     * Save a com_local_transfered.
     * 
     * @param com_local_transfered the entity to save
     * @return the persisted entity
     */
    Com_local_transfered save(Com_local_transfered com_local_transfered);

    /**
     *  Get all the com_local_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_local_transfered> findAll(Pageable pageable);

    /**
     *  Get the "id" com_local_transfered.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_local_transfered findOne(Long id);

    /**
     *  Delete the "id" com_local_transfered.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
