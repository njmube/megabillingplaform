package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_incotermService;
import org.megapractical.billingplatform.domain.Freecom_incoterm;
import org.megapractical.billingplatform.repository.Freecom_incotermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_incoterm.
 */
@Service
@Transactional
public class Freecom_incotermServiceImpl implements Freecom_incotermService{

    private final Logger log = LoggerFactory.getLogger(Freecom_incotermServiceImpl.class);
    
    @Inject
    private Freecom_incotermRepository freecom_incotermRepository;
    
    /**
     * Save a freecom_incoterm.
     * 
     * @param freecom_incoterm the entity to save
     * @return the persisted entity
     */
    public Freecom_incoterm save(Freecom_incoterm freecom_incoterm) {
        log.debug("Request to save Freecom_incoterm : {}", freecom_incoterm);
        Freecom_incoterm result = freecom_incotermRepository.save(freecom_incoterm);
        return result;
    }

    /**
     *  Get all the freecom_incoterms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_incoterm> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_incoterms");
        Page<Freecom_incoterm> result = freecom_incotermRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_incoterm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_incoterm findOne(Long id) {
        log.debug("Request to get Freecom_incoterm : {}", id);
        Freecom_incoterm freecom_incoterm = freecom_incotermRepository.findOne(id);
        return freecom_incoterm;
    }

    /**
     *  Delete the  freecom_incoterm by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_incoterm : {}", id);
        freecom_incotermRepository.delete(id);
    }
}
