package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_chargeService;
import org.megapractical.billingplatform.domain.Freecom_charge;
import org.megapractical.billingplatform.repository.Freecom_chargeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_charge.
 */
@Service
@Transactional
public class Freecom_chargeServiceImpl implements Freecom_chargeService{

    private final Logger log = LoggerFactory.getLogger(Freecom_chargeServiceImpl.class);
    
    @Inject
    private Freecom_chargeRepository freecom_chargeRepository;
    
    /**
     * Save a freecom_charge.
     * 
     * @param freecom_charge the entity to save
     * @return the persisted entity
     */
    public Freecom_charge save(Freecom_charge freecom_charge) {
        log.debug("Request to save Freecom_charge : {}", freecom_charge);
        Freecom_charge result = freecom_chargeRepository.save(freecom_charge);
        return result;
    }

    /**
     *  Get all the freecom_charges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_charge> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_charges");
        Page<Freecom_charge> result = freecom_chargeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_charge by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_charge findOne(Long id) {
        log.debug("Request to get Freecom_charge : {}", id);
        Freecom_charge freecom_charge = freecom_chargeRepository.findOne(id);
        return freecom_charge;
    }

    /**
     *  Delete the  freecom_charge by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_charge : {}", id);
        freecom_chargeRepository.delete(id);
    }
}
