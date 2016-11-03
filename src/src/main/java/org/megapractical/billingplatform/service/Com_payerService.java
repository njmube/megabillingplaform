package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_payer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_payer.
 */
public interface Com_payerService {

    /**
     * Save a com_payer.
     * 
     * @param com_payer the entity to save
     * @return the persisted entity
     */
    Com_payer save(Com_payer com_payer);

    /**
     *  Get all the com_payers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_payer> findAll(Pageable pageable);

    /**
     *  Get the "id" com_payer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_payer findOne(Long id);

    /**
     *  Delete the "id" com_payer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
