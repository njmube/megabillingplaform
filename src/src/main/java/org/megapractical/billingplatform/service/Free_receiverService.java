package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_receiver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_receiver.
 */
public interface Free_receiverService {

    /**
     * Save a free_receiver.
     * 
     * @param free_receiver the entity to save
     * @return the persisted entity
     */
    Free_receiver save(Free_receiver free_receiver);

    /**
     *  Get all the free_receivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_receiver> findAll(Pageable pageable);

    /**
     *  Get the "id" free_receiver.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_receiver findOne(Long id);

    /**
     *  Delete the "id" free_receiver.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
