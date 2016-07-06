package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_tax_transfered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_tax_transfered.
 */
public interface Free_tax_transferedService {

    /**
     * Save a free_tax_transfered.
     * 
     * @param free_tax_transfered the entity to save
     * @return the persisted entity
     */
    Free_tax_transfered save(Free_tax_transfered free_tax_transfered);

    /**
     *  Get all the free_tax_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_tax_transfered> findAll(Pageable pageable);

    /**
     *  Get the "id" free_tax_transfered.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_tax_transfered findOne(Long id);

    /**
     *  Delete the "id" free_tax_transfered.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
