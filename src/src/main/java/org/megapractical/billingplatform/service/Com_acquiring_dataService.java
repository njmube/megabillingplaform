package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_acquiring_data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_acquiring_data.
 */
public interface Com_acquiring_dataService {

    /**
     * Save a com_acquiring_data.
     * 
     * @param com_acquiring_data the entity to save
     * @return the persisted entity
     */
    Com_acquiring_data save(Com_acquiring_data com_acquiring_data);

    /**
     *  Get all the com_acquiring_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_acquiring_data> findAll(Pageable pageable);

    /**
     *  Get the "id" com_acquiring_data.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_acquiring_data findOne(Long id);

    /**
     *  Delete the "id" com_acquiring_data.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
