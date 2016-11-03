package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_apaw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_apaw.
 */
public interface Com_apawService {

    /**
     * Save a com_apaw.
     * 
     * @param com_apaw the entity to save
     * @return the persisted entity
     */
    Com_apaw save(Com_apaw com_apaw);

    /**
     *  Get all the com_apaws.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_apaw> findAll(Pageable pageable);

    /**
     *  Get the "id" com_apaw.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_apaw findOne(Long id);

    /**
     *  Delete the "id" com_apaw.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
