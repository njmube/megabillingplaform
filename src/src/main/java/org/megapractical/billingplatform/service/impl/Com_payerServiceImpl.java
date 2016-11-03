package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_payerService;
import org.megapractical.billingplatform.domain.Com_payer;
import org.megapractical.billingplatform.repository.Com_payerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_payer.
 */
@Service
@Transactional
public class Com_payerServiceImpl implements Com_payerService{

    private final Logger log = LoggerFactory.getLogger(Com_payerServiceImpl.class);
    
    @Inject
    private Com_payerRepository com_payerRepository;
    
    /**
     * Save a com_payer.
     * 
     * @param com_payer the entity to save
     * @return the persisted entity
     */
    public Com_payer save(Com_payer com_payer) {
        log.debug("Request to save Com_payer : {}", com_payer);
        Com_payer result = com_payerRepository.save(com_payer);
        return result;
    }

    /**
     *  Get all the com_payers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_payer> findAll(Pageable pageable) {
        log.debug("Request to get all Com_payers");
        Page<Com_payer> result = com_payerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_payer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_payer findOne(Long id) {
        log.debug("Request to get Com_payer : {}", id);
        Com_payer com_payer = com_payerRepository.findOne(id);
        return com_payer;
    }

    /**
     *  Delete the  com_payer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_payer : {}", id);
        com_payerRepository.delete(id);
    }
}
