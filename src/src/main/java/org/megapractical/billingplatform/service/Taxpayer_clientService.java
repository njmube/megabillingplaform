package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_client.
 */
public interface Taxpayer_clientService {

    /**
     * Save a taxpayer_client.
     * 
     * @param taxpayer_client the entity to save
     * @return the persisted entity
     */
    Taxpayer_client save(Taxpayer_client taxpayer_client);

    /**
     *  Get all the taxpayer_clients.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_client> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_client.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_client findOne(Long id);

    /**
     *  Delete the "id" taxpayer_client.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
