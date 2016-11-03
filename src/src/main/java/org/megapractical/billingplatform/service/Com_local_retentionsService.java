package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_local_retentions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_local_retentions.
 */
public interface Com_local_retentionsService {

    /**
     * Save a com_local_retentions.
     * 
     * @param com_local_retentions the entity to save
     * @return the persisted entity
     */
    Com_local_retentions save(Com_local_retentions com_local_retentions);

    /**
     *  Get all the com_local_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_local_retentions> findAll(Pageable pageable);

    /**
     *  Get the "id" com_local_retentions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_local_retentions findOne(Long id);

    /**
     *  Delete the "id" com_local_retentions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
