package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_accountingService;
import org.megapractical.billingplatform.domain.Com_accounting;
import org.megapractical.billingplatform.repository.Com_accountingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_accounting.
 */
@Service
@Transactional
public class Com_accountingServiceImpl implements Com_accountingService{

    private final Logger log = LoggerFactory.getLogger(Com_accountingServiceImpl.class);
    
    @Inject
    private Com_accountingRepository com_accountingRepository;
    
    /**
     * Save a com_accounting.
     * 
     * @param com_accounting the entity to save
     * @return the persisted entity
     */
    public Com_accounting save(Com_accounting com_accounting) {
        log.debug("Request to save Com_accounting : {}", com_accounting);
        Com_accounting result = com_accountingRepository.save(com_accounting);
        return result;
    }

    /**
     *  Get all the com_accountings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_accounting> findAll(Pageable pageable) {
        log.debug("Request to get all Com_accountings");
        Page<Com_accounting> result = com_accountingRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_accounting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_accounting findOne(Long id) {
        log.debug("Request to get Com_accounting : {}", id);
        Com_accounting com_accounting = com_accountingRepository.findOne(id);
        return com_accounting;
    }

    /**
     *  Delete the  com_accounting by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_accounting : {}", id);
        com_accountingRepository.delete(id);
    }
}
