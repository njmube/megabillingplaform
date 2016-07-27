package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_state_event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_state_event.
 */
public interface C_state_eventService {

    /**
     * Save a c_state_event.
     * 
     * @param c_state_event the entity to save
     * @return the persisted entity
     */
    C_state_event save(C_state_event c_state_event);

    /**
     *  Get all the c_state_events.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_state_event> findAll(Pageable pageable);

    /**
     *  Get the "id" c_state_event.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_state_event findOne(Long id);

    /**
     *  Delete the "id" c_state_event.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
