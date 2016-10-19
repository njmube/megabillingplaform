package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_request_confirm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_request_confirm.
 */
public interface Taxpayer_request_confirmService {

    /**
     * Save a taxpayer_request_confirm.
     * 
     * @param taxpayer_request_confirm the entity to save
     * @return the persisted entity
     */
    Taxpayer_request_confirm save(Taxpayer_request_confirm taxpayer_request_confirm);

    /**
     *  Get all the taxpayer_request_confirms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_request_confirm> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_request_confirm.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_request_confirm findOne(Long id);

    /**
     *  Delete the "id" taxpayer_request_confirm.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
