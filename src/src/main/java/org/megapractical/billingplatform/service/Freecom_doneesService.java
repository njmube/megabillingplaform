package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_donees;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_donees.
 */
public interface Freecom_doneesService {

    /**
     * Save a freecom_donees.
     * 
     * @param freecom_donees the entity to save
     * @return the persisted entity
     */
    Freecom_donees save(Freecom_donees freecom_donees);

    /**
     *  Get all the freecom_donees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_donees> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_donees.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_donees findOne(Long id);

    /**
     *  Delete the "id" freecom_donees.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
