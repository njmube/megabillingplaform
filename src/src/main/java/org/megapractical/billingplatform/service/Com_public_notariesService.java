package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_public_notaries;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_public_notaries.
 */
public interface Com_public_notariesService {

    /**
     * Save a com_public_notaries.
     * 
     * @param com_public_notaries the entity to save
     * @return the persisted entity
     */
    Com_public_notaries save(Com_public_notaries com_public_notaries);

    /**
     *  Get all the com_public_notaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_public_notaries> findAll(Pageable pageable);

    /**
     *  Get the "id" com_public_notaries.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_public_notaries findOne(Long id);

    /**
     *  Delete the "id" com_public_notaries.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
