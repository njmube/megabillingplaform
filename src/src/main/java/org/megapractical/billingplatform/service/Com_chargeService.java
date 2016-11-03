package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_charge.
 */
public interface Com_chargeService {

    /**
     * Save a com_charge.
     * 
     * @param com_charge the entity to save
     * @return the persisted entity
     */
    Com_charge save(Com_charge com_charge);

    /**
     *  Get all the com_charges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_charge> findAll(Pageable pageable);

    /**
     *  Get the "id" com_charge.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_charge findOne(Long id);

    /**
     *  Delete the "id" com_charge.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
