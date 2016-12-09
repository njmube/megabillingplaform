package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_desc_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_desc_state.
 */
public interface Com_desc_stateService {

    /**
     * Save a com_desc_state.
     * 
     * @param com_desc_state the entity to save
     * @return the persisted entity
     */
    Com_desc_state save(Com_desc_state com_desc_state);

    /**
     *  Get all the com_desc_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_desc_state> findAll(Pageable pageable);

    /**
     *  Get the "id" com_desc_state.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_desc_state findOne(Long id);

    /**
     *  Delete the "id" com_desc_state.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
