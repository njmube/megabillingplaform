package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_tax_rate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_tax_rate.
 */
public interface C_tax_rateService {

    /**
     * Save a c_tax_rate.
     * 
     * @param c_tax_rate the entity to save
     * @return the persisted entity
     */
    C_tax_rate save(C_tax_rate c_tax_rate);

    /**
     *  Get all the c_tax_rates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_tax_rate> findAll(Pageable pageable);

    /**
     *  Get the "id" c_tax_rate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_tax_rate findOne(Long id);

    /**
     *  Delete the "id" c_tax_rate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
