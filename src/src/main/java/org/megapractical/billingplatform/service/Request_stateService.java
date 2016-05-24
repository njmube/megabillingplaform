package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Request_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Request_state.
 */
public interface Request_stateService {

    /**
     * Save a request_state.
     * 
     * @param request_state the entity to save
     * @return the persisted entity
     */
    Request_state save(Request_state request_state);

    /**
     *  Get all the request_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Request_state> findAll(Pageable pageable);

    /**
     *  Get the "id" request_state.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Request_state findOne(Long id);

    /**
     *  Delete the "id" request_state.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
