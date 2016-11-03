package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_storeroom_paybill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_storeroom_paybill.
 */
public interface Com_storeroom_paybillService {

    /**
     * Save a com_storeroom_paybill.
     * 
     * @param com_storeroom_paybill the entity to save
     * @return the persisted entity
     */
    Com_storeroom_paybill save(Com_storeroom_paybill com_storeroom_paybill);

    /**
     *  Get all the com_storeroom_paybills.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_storeroom_paybill> findAll(Pageable pageable);

    /**
     *  Get the "id" com_storeroom_paybill.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_storeroom_paybill findOne(Long id);

    /**
     *  Delete the "id" com_storeroom_paybill.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
