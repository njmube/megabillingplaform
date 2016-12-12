package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_notary_data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_notary_data.
 */
public interface Freecom_notary_dataService {

    /**
     * Save a freecom_notary_data.
     * 
     * @param freecom_notary_data the entity to save
     * @return the persisted entity
     */
    Freecom_notary_data save(Freecom_notary_data freecom_notary_data);

    /**
     *  Get all the freecom_notary_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_notary_data> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_notary_data.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_notary_data findOne(Long id);

    /**
     *  Delete the "id" freecom_notary_data.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
