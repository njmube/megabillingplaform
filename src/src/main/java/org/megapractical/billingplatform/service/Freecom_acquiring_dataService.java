package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_acquiring_data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_acquiring_data.
 */
public interface Freecom_acquiring_dataService {

    /**
     * Save a freecom_acquiring_data.
     * 
     * @param freecom_acquiring_data the entity to save
     * @return the persisted entity
     */
    Freecom_acquiring_data save(Freecom_acquiring_data freecom_acquiring_data);

    /**
     *  Get all the freecom_acquiring_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_acquiring_data> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_acquiring_data.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_acquiring_data findOne(Long id);

    /**
     *  Delete the "id" freecom_acquiring_data.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
