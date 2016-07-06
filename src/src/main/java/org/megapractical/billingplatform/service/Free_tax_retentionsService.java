package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_tax_retentions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_tax_retentions.
 */
public interface Free_tax_retentionsService {

    /**
     * Save a free_tax_retentions.
     * 
     * @param free_tax_retentions the entity to save
     * @return the persisted entity
     */
    Free_tax_retentions save(Free_tax_retentions free_tax_retentions);

    /**
     *  Get all the free_tax_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_tax_retentions> findAll(Pageable pageable);

    /**
     *  Get the "id" free_tax_retentions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_tax_retentions findOne(Long id);

    /**
     *  Delete the "id" free_tax_retentions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
