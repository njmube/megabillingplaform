package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_clientService;
import org.megapractical.billingplatform.domain.Taxpayer_client;
import org.megapractical.billingplatform.repository.Taxpayer_clientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_client.
 */
@Service
@Transactional
public class Taxpayer_clientServiceImpl implements Taxpayer_clientService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_clientServiceImpl.class);
    
    @Inject
    private Taxpayer_clientRepository taxpayer_clientRepository;
    
    /**
     * Save a taxpayer_client.
     * 
     * @param taxpayer_client the entity to save
     * @return the persisted entity
     */
    public Taxpayer_client save(Taxpayer_client taxpayer_client) {
        log.debug("Request to save Taxpayer_client : {}", taxpayer_client);
        Taxpayer_client result = taxpayer_clientRepository.save(taxpayer_client);
        return result;
    }

    /**
     *  Get all the taxpayer_clients.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_client> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_clients");
        Page<Taxpayer_client> result = taxpayer_clientRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_client by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_client findOne(Long id) {
        log.debug("Request to get Taxpayer_client : {}", id);
        Taxpayer_client taxpayer_client = taxpayer_clientRepository.findOne(id);
        return taxpayer_client;
    }

    /**
     *  Delete the  taxpayer_client by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_client : {}", id);
        taxpayer_clientRepository.delete(id);
    }
}
