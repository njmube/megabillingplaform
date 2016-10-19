package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_request.
 */
public interface Taxpayer_requestService {

    /**
     * Save a taxpayer_request.
     * 
     * @param taxpayer_request the entity to save
     * @return the persisted entity
     */
    Taxpayer_request save(Taxpayer_request taxpayer_request);

    /**
     *  Get all the taxpayer_requests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_request> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_request.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_request findOne(Long id);

    /**
     *  Delete the "id" taxpayer_request.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
