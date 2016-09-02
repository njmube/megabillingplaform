package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_address.
 */
public interface Tax_addressService {

    /**
     * Save a tax_address.
     * 
     * @param tax_address the entity to save
     * @return the persisted entity
     */
    Tax_address save(Tax_address tax_address);

    /**
     *  Get all the tax_addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_address> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_address.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_address findOne(Long id);

    /**
     *  Delete the "id" tax_address.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
