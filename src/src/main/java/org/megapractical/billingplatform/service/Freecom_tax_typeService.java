package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_tax_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_tax_type.
 */
public interface Freecom_tax_typeService {

    /**
     * Save a freecom_tax_type.
     * 
     * @param freecom_tax_type the entity to save
     * @return the persisted entity
     */
    Freecom_tax_type save(Freecom_tax_type freecom_tax_type);

    /**
     *  Get all the freecom_tax_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_tax_type> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_tax_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_tax_type findOne(Long id);

    /**
     *  Delete the "id" freecom_tax_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
