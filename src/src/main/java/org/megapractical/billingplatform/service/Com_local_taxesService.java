package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_local_taxes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_local_taxes.
 */
public interface Com_local_taxesService {

    /**
     * Save a com_local_taxes.
     * 
     * @param com_local_taxes the entity to save
     * @return the persisted entity
     */
    Com_local_taxes save(Com_local_taxes com_local_taxes);

    /**
     *  Get all the com_local_taxes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_local_taxes> findAll(Pageable pageable);

    /**
     *  Get the "id" com_local_taxes.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_local_taxes findOne(Long id);

    /**
     *  Delete the "id" com_local_taxes.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
