package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Billing_account_stateService;
import org.megapractical.billingplatform.domain.Billing_account_state;
import org.megapractical.billingplatform.repository.Billing_account_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Billing_account_state.
 */
@Service
@Transactional
public class Billing_account_stateServiceImpl implements Billing_account_stateService{

    private final Logger log = LoggerFactory.getLogger(Billing_account_stateServiceImpl.class);
    
    @Inject
    private Billing_account_stateRepository billing_account_stateRepository;
    
    /**
     * Save a billing_account_state.
     * 
     * @param billing_account_state the entity to save
     * @return the persisted entity
     */
    public Billing_account_state save(Billing_account_state billing_account_state) {
        log.debug("Request to save Billing_account_state : {}", billing_account_state);
        Billing_account_state result = billing_account_stateRepository.save(billing_account_state);
        return result;
    }

    /**
     *  Get all the billing_account_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Billing_account_state> findAll(Pageable pageable) {
        log.debug("Request to get all Billing_account_states");
        Page<Billing_account_state> result = billing_account_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one billing_account_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Billing_account_state findOne(Long id) {
        log.debug("Request to get Billing_account_state : {}", id);
        Billing_account_state billing_account_state = billing_account_stateRepository.findOne(id);
        return billing_account_state;
    }

    /**
     *  Delete the  billing_account_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Billing_account_state : {}", id);
        billing_account_stateRepository.delete(id);
    }
}
