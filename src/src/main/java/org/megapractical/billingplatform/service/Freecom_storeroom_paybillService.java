package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_storeroom_paybill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_storeroom_paybill.
 */
public interface Freecom_storeroom_paybillService {

    /**
     * Save a freecom_storeroom_paybill.
     * 
     * @param freecom_storeroom_paybill the entity to save
     * @return the persisted entity
     */
    Freecom_storeroom_paybill save(Freecom_storeroom_paybill freecom_storeroom_paybill);

    /**
     *  Get all the freecom_storeroom_paybills.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_storeroom_paybill> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_storeroom_paybill.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_storeroom_paybill findOne(Long id);

    /**
     *  Delete the "id" freecom_storeroom_paybill.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
