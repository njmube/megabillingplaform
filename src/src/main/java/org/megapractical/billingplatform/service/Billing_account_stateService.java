package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Billing_account_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Billing_account_state.
 */
public interface Billing_account_stateService {

    /**
     * Save a billing_account_state.
     * 
     * @param billing_account_state the entity to save
     * @return the persisted entity
     */
    Billing_account_state save(Billing_account_state billing_account_state);

    /**
     *  Get all the billing_account_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Billing_account_state> findAll(Pageable pageable);

    /**
     *  Get the "id" billing_account_state.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Billing_account_state findOne(Long id);

    /**
     *  Delete the "id" billing_account_state.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
