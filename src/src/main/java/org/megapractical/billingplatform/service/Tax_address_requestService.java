package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Tax_address_request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Tax_address_request.
 */
public interface Tax_address_requestService {

    /**
     * Save a tax_address_request.
     * 
     * @param tax_address_request the entity to save
     * @return the persisted entity
     */
    Tax_address_request save(Tax_address_request tax_address_request);

    /**
     *  Get all the tax_address_requests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tax_address_request> findAll(Pageable pageable);

    /**
     *  Get the "id" tax_address_request.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Tax_address_request findOne(Long id);

    /**
     *  Delete the "id" tax_address_request.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
