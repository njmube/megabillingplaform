package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.AccountingService;
import org.megapractical.billingplatform.domain.Accounting;
import org.megapractical.billingplatform.repository.AccountingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Accounting.
 */
@Service
@Transactional
public class AccountingServiceImpl implements AccountingService{

    private final Logger log = LoggerFactory.getLogger(AccountingServiceImpl.class);
    
    @Inject
    private AccountingRepository accountingRepository;
    
    /**
     * Save a accounting.
     * 
     * @param accounting the entity to save
     * @return the persisted entity
     */
    public Accounting save(Accounting accounting) {
        log.debug("Request to save Accounting : {}", accounting);
        Accounting result = accountingRepository.save(accounting);
        return result;
    }

    /**
     *  Get all the accountings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Accounting> findAll(Pageable pageable) {
        log.debug("Request to get all Accountings");
        Page<Accounting> result = accountingRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one accounting by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Accounting findOne(Long id) {
        log.debug("Request to get Accounting : {}", id);
        Accounting accounting = accountingRepository.findOne(id);
        return accounting;
    }

    /**
     *  Delete the  accounting by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Accounting : {}", id);
        accountingRepository.delete(id);
    }
}
