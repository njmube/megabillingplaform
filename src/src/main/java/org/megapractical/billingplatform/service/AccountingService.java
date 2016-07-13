package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Accounting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Accounting.
 */
public interface AccountingService {

    /**
     * Save a accounting.
     * 
     * @param accounting the entity to save
     * @return the persisted entity
     */
    Accounting save(Accounting accounting);

    /**
     *  Get all the accountings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Accounting> findAll(Pageable pageable);

    /**
     *  Get the "id" accounting.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Accounting findOne(Long id);

    /**
     *  Delete the "id" accounting.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
