package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_chargeService;
import org.megapractical.billingplatform.domain.Com_charge;
import org.megapractical.billingplatform.repository.Com_chargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_charge.
 */
@Service
@Transactional
public class Com_chargeServiceImpl implements Com_chargeService{

    private final Logger log = LoggerFactory.getLogger(Com_chargeServiceImpl.class);
    
    @Inject
    private Com_chargeRepository com_chargeRepository;
    
    /**
     * Save a com_charge.
     * 
     * @param com_charge the entity to save
     * @return the persisted entity
     */
    public Com_charge save(Com_charge com_charge) {
        log.debug("Request to save Com_charge : {}", com_charge);
        Com_charge result = com_chargeRepository.save(com_charge);
        return result;
    }

    /**
     *  Get all the com_charges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_charge> findAll(Pageable pageable) {
        log.debug("Request to get all Com_charges");
        Page<Com_charge> result = com_chargeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_charge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_charge findOne(Long id) {
        log.debug("Request to get Com_charge : {}", id);
        Com_charge com_charge = com_chargeRepository.findOne(id);
        return com_charge;
    }

    /**
     *  Delete the  com_charge by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_charge : {}", id);
        com_chargeRepository.delete(id);
    }
}
