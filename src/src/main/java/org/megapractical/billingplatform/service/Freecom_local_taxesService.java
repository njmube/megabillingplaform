package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_local_taxes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_local_taxes.
 */
public interface Freecom_local_taxesService {

    /**
     * Save a freecom_local_taxes.
     * 
     * @param freecom_local_taxes the entity to save
     * @return the persisted entity
     */
    Freecom_local_taxes save(Freecom_local_taxes freecom_local_taxes);

    /**
     *  Get all the freecom_local_taxes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_local_taxes> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_local_taxes.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_local_taxes findOne(Long id);

    /**
     *  Delete the "id" freecom_local_taxes.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
