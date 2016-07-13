package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_airlineService;
import org.megapractical.billingplatform.domain.Freecom_airline;
import org.megapractical.billingplatform.repository.Freecom_airlineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_airline.
 */
@Service
@Transactional
public class Freecom_airlineServiceImpl implements Freecom_airlineService{

    private final Logger log = LoggerFactory.getLogger(Freecom_airlineServiceImpl.class);
    
    @Inject
    private Freecom_airlineRepository freecom_airlineRepository;
    
    /**
     * Save a freecom_airline.
     * 
     * @param freecom_airline the entity to save
     * @return the persisted entity
     */
    public Freecom_airline save(Freecom_airline freecom_airline) {
        log.debug("Request to save Freecom_airline : {}", freecom_airline);
        Freecom_airline result = freecom_airlineRepository.save(freecom_airline);
        return result;
    }

    /**
     *  Get all the freecom_airlines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_airline> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_airlines");
        Page<Freecom_airline> result = freecom_airlineRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_airline by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_airline findOne(Long id) {
        log.debug("Request to get Freecom_airline : {}", id);
        Freecom_airline freecom_airline = freecom_airlineRepository.findOne(id);
        return freecom_airline;
    }

    /**
     *  Delete the  freecom_airline by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_airline : {}", id);
        freecom_airlineRepository.delete(id);
    }
}
