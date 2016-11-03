package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_accounting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_accounting.
 */
public interface Com_accountingService {

    /**
     * Save a com_accounting.
     * 
     * @param com_accounting the entity to save
     * @return the persisted entity
     */
    Com_accounting save(Com_accounting com_accounting);

    /**
     *  Get all the com_accountings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_accounting> findAll(Pageable pageable);

    /**
     *  Get the "id" com_accounting.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_accounting findOne(Long id);

    /**
     *  Delete the "id" com_accounting.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
