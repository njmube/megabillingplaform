package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Client_address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Client_address.
 */
public interface Client_addressService {

    /**
     * Save a client_address.
     * 
     * @param client_address the entity to save
     * @return the persisted entity
     */
    Client_address save(Client_address client_address);

    /**
     *  Get all the client_addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Client_address> findAll(Pageable pageable);

    /**
     *  Get the "id" client_address.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Client_address findOne(Long id);

    /**
     *  Delete the "id" client_address.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
