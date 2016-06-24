package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_retentions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_retentions.
 */
public interface Tax_retentionsService {

    /**
     * Save a tax_retentions.
     * 
     * @param tax_retentions the entity to save
     * @return the persisted entity
     */
    Tax_retentions save(Tax_retentions tax_retentions);

    /**
     *  Get all the tax_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_retentions> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_retentions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_retentions findOne(Long id);

    /**
     *  Delete the "id" tax_retentions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
