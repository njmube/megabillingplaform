package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_charge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_charge.
 */
public interface Freecom_chargeService {

    /**
     * Save a freecom_charge.
     * 
     * @param freecom_charge the entity to save
     * @return the persisted entity
     */
    Freecom_charge save(Freecom_charge freecom_charge);

    /**
     *  Get all the freecom_charges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_charge> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_charge.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_charge findOne(Long id);

    /**
     *  Delete the "id" freecom_charge.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
