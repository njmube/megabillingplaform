package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_types;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_types.
 */
public interface Tax_typesService {

    /**
     * Save a tax_types.
     * 
     * @param tax_types the entity to save
     * @return the persisted entity
     */
    Tax_types save(Tax_types tax_types);

    /**
     *  Get all the tax_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_types> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_types.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_types findOne(Long id);

    /**
     *  Delete the "id" tax_types.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
