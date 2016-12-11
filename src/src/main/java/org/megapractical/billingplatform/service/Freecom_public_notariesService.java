package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_public_notaries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_public_notaries.
 */
public interface Freecom_public_notariesService {

    /**
     * Save a freecom_public_notaries.
     * 
     * @param freecom_public_notaries the entity to save
     * @return the persisted entity
     */
    Freecom_public_notaries save(Freecom_public_notaries freecom_public_notaries);

    /**
     *  Get all the freecom_public_notaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_public_notaries> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_public_notaries.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_public_notaries findOne(Long id);

    /**
     *  Delete the "id" freecom_public_notaries.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
