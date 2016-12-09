package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Type_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Type_state.
 */
public interface Type_stateService {

    /**
     * Save a type_state.
     * 
     * @param type_state the entity to save
     * @return the persisted entity
     */
    Type_state save(Type_state type_state);

    /**
     *  Get all the type_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Type_state> findAll(Pageable pageable);

    /**
     *  Get the "id" type_state.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Type_state findOne(Long id);

    /**
     *  Delete the "id" type_state.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
