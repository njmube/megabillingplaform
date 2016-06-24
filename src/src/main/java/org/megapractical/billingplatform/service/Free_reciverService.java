package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_reciver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_reciver.
 */
public interface Free_reciverService {

    /**
     * Save a free_reciver.
     * 
     * @param free_reciver the entity to save
     * @return the persisted entity
     */
    Free_reciver save(Free_reciver free_reciver);

    /**
     *  Get all the free_recivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_reciver> findAll(Pageable pageable);

    /**
     *  Get the "id" free_reciver.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_reciver findOne(Long id);

    /**
     *  Delete the "id" free_reciver.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
