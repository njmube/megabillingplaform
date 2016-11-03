package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_incotermService;
import org.megapractical.billingplatform.domain.Com_incoterm;
import org.megapractical.billingplatform.repository.Com_incotermRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_incoterm.
 */
@Service
@Transactional
public class Com_incotermServiceImpl implements Com_incotermService{

    private final Logger log = LoggerFactory.getLogger(Com_incotermServiceImpl.class);
    
    @Inject
    private Com_incotermRepository com_incotermRepository;
    
    /**
     * Save a com_incoterm.
     * 
     * @param com_incoterm the entity to save
     * @return the persisted entity
     */
    public Com_incoterm save(Com_incoterm com_incoterm) {
        log.debug("Request to save Com_incoterm : {}", com_incoterm);
        Com_incoterm result = com_incotermRepository.save(com_incoterm);
        return result;
    }

    /**
     *  Get all the com_incoterms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_incoterm> findAll(Pageable pageable) {
        log.debug("Request to get all Com_incoterms");
        Page<Com_incoterm> result = com_incotermRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_incoterm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_incoterm findOne(Long id) {
        log.debug("Request to get Com_incoterm : {}", id);
        Com_incoterm com_incoterm = com_incotermRepository.findOne(id);
        return com_incoterm;
    }

    /**
     *  Delete the  com_incoterm by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_incoterm : {}", id);
        com_incotermRepository.delete(id);
    }
}
