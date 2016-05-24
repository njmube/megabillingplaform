package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Voucher_stateService;
import org.megapractical.billingplatform.domain.Voucher_state;
import org.megapractical.billingplatform.repository.Voucher_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Voucher_state.
 */
@Service
@Transactional
public class Voucher_stateServiceImpl implements Voucher_stateService{

    private final Logger log = LoggerFactory.getLogger(Voucher_stateServiceImpl.class);
    
    @Inject
    private Voucher_stateRepository voucher_stateRepository;
    
    /**
     * Save a voucher_state.
     * 
     * @param voucher_state the entity to save
     * @return the persisted entity
     */
    public Voucher_state save(Voucher_state voucher_state) {
        log.debug("Request to save Voucher_state : {}", voucher_state);
        Voucher_state result = voucher_stateRepository.save(voucher_state);
        return result;
    }

    /**
     *  Get all the voucher_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Voucher_state> findAll(Pageable pageable) {
        log.debug("Request to get all Voucher_states");
        Page<Voucher_state> result = voucher_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one voucher_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Voucher_state findOne(Long id) {
        log.debug("Request to get Voucher_state : {}", id);
        Voucher_state voucher_state = voucher_stateRepository.findOne(id);
        return voucher_state;
    }

    /**
     *  Delete the  voucher_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Voucher_state : {}", id);
        voucher_stateRepository.delete(id);
    }
}
