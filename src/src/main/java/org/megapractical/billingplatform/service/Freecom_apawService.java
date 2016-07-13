package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_apaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_apaw.
 */
public interface Freecom_apawService {

    /**
     * Save a freecom_apaw.
     * 
     * @param freecom_apaw the entity to save
     * @return the persisted entity
     */
    Freecom_apaw save(Freecom_apaw freecom_apaw);

    /**
     *  Get all the freecom_apaws.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_apaw> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_apaw.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_apaw findOne(Long id);

    /**
     *  Delete the "id" freecom_apaw.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
