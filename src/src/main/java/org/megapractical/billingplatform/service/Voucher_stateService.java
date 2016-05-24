package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Voucher_state;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Voucher_state.
 */
public interface Voucher_stateService {

    /**
     * Save a voucher_state.
     * 
     * @param voucher_state the entity to save
     * @return the persisted entity
     */
    Voucher_state save(Voucher_state voucher_state);

    /**
     *  Get all the voucher_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Voucher_state> findAll(Pageable pageable);

    /**
     *  Get the "id" voucher_state.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Voucher_state findOne(Long id);

    /**
     *  Delete the "id" voucher_state.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
