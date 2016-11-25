package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Client.
 */
public interface ClientService {

    /**
     * Save a client.
     * 
     * @param client the entity to save
     * @return the persisted entity
     */
    Client save(Client client);

    /**
     *  Get all the clients.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Client> findAll(Pageable pageable);

    /**
     *  Get the "id" client.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Client findOne(Long id);

    /**
     *  Delete the "id" client.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
