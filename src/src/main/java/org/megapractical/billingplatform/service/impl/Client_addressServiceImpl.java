package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Client_addressService;
import org.megapractical.billingplatform.domain.Client_address;
import org.megapractical.billingplatform.repository.Client_addressRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Client_address.
 */
@Service
@Transactional
public class Client_addressServiceImpl implements Client_addressService{

    private final Logger log = LoggerFactory.getLogger(Client_addressServiceImpl.class);
    
    @Inject
    private Client_addressRepository client_addressRepository;
    
    /**
     * Save a client_address.
     * 
     * @param client_address the entity to save
     * @return the persisted entity
     */
    public Client_address save(Client_address client_address) {
        log.debug("Request to save Client_address : {}", client_address);
        Client_address result = client_addressRepository.save(client_address);
        return result;
    }

    /**
     *  Get all the client_addresses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Client_address> findAll(Pageable pageable) {
        log.debug("Request to get all Client_addresses");
        Page<Client_address> result = client_addressRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one client_address by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Client_address findOne(Long id) {
        log.debug("Request to get Client_address : {}", id);
        Client_address client_address = client_addressRepository.findOne(id);
        return client_address;
    }

    /**
     *  Delete the  client_address by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Client_address : {}", id);
        client_addressRepository.delete(id);
    }
}
