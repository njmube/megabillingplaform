package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_determined;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_determined.
 */
public interface Freecom_determinedService {

    /**
     * Save a freecom_determined.
     * 
     * @param freecom_determined the entity to save
     * @return the persisted entity
     */
    Freecom_determined save(Freecom_determined freecom_determined);

    /**
     *  Get all the freecom_determineds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_determined> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_determined.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_determined findOne(Long id);

    /**
     *  Delete the "id" freecom_determined.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
