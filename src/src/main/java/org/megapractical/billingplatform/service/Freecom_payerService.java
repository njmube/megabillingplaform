package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_payer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_payer.
 */
public interface Freecom_payerService {

    /**
     * Save a freecom_payer.
     * 
     * @param freecom_payer the entity to save
     * @return the persisted entity
     */
    Freecom_payer save(Freecom_payer freecom_payer);

    /**
     *  Get all the freecom_payers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_payer> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_payer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_payer findOne(Long id);

    /**
     *  Delete the "id" freecom_payer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
