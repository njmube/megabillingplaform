package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_payerService;
import org.megapractical.billingplatform.domain.Freecom_payer;
import org.megapractical.billingplatform.repository.Freecom_payerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_payer.
 */
@Service
@Transactional
public class Freecom_payerServiceImpl implements Freecom_payerService{

    private final Logger log = LoggerFactory.getLogger(Freecom_payerServiceImpl.class);
    
    @Inject
    private Freecom_payerRepository freecom_payerRepository;
    
    /**
     * Save a freecom_payer.
     * 
     * @param freecom_payer the entity to save
     * @return the persisted entity
     */
    public Freecom_payer save(Freecom_payer freecom_payer) {
        log.debug("Request to save Freecom_payer : {}", freecom_payer);
        Freecom_payer result = freecom_payerRepository.save(freecom_payer);
        return result;
    }

    /**
     *  Get all the freecom_payers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_payer> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_payers");
        Page<Freecom_payer> result = freecom_payerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_payer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_payer findOne(Long id) {
        log.debug("Request to get Freecom_payer : {}", id);
        Freecom_payer freecom_payer = freecom_payerRepository.findOne(id);
        return freecom_payer;
    }

    /**
     *  Delete the  freecom_payer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_payer : {}", id);
        freecom_payerRepository.delete(id);
    }
}
