package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_transfered;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_transfered.
 */
public interface Tax_transferedService {

    /**
     * Save a tax_transfered.
     * 
     * @param tax_transfered the entity to save
     * @return the persisted entity
     */
    Tax_transfered save(Tax_transfered tax_transfered);

    /**
     *  Get all the tax_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_transfered> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_transfered.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_transfered findOne(Long id);

    /**
     *  Delete the "id" tax_transfered.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
