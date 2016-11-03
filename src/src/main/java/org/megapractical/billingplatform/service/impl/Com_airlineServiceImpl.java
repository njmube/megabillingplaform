package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_airlineService;
import org.megapractical.billingplatform.domain.Com_airline;
import org.megapractical.billingplatform.repository.Com_airlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_airline.
 */
@Service
@Transactional
public class Com_airlineServiceImpl implements Com_airlineService{

    private final Logger log = LoggerFactory.getLogger(Com_airlineServiceImpl.class);
    
    @Inject
    private Com_airlineRepository com_airlineRepository;
    
    /**
     * Save a com_airline.
     * 
     * @param com_airline the entity to save
     * @return the persisted entity
     */
    public Com_airline save(Com_airline com_airline) {
        log.debug("Request to save Com_airline : {}", com_airline);
        Com_airline result = com_airlineRepository.save(com_airline);
        return result;
    }

    /**
     *  Get all the com_airlines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_airline> findAll(Pageable pageable) {
        log.debug("Request to get all Com_airlines");
        Page<Com_airline> result = com_airlineRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_airline by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_airline findOne(Long id) {
        log.debug("Request to get Com_airline : {}", id);
        Com_airline com_airline = com_airlineRepository.findOne(id);
        return com_airline;
    }

    /**
     *  Delete the  com_airline by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_airline : {}", id);
        com_airlineRepository.delete(id);
    }
}
