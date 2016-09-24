package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_mail_accountsService;
import org.megapractical.billingplatform.domain.Taxpayer_mail_accounts;
import org.megapractical.billingplatform.repository.Taxpayer_mail_accountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_mail_accounts.
 */
@Service
@Transactional
public class Taxpayer_mail_accountsServiceImpl implements Taxpayer_mail_accountsService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_mail_accountsServiceImpl.class);
    
    @Inject
    private Taxpayer_mail_accountsRepository taxpayer_mail_accountsRepository;
    
    /**
     * Save a taxpayer_mail_accounts.
     * 
     * @param taxpayer_mail_accounts the entity to save
     * @return the persisted entity
     */
    public Taxpayer_mail_accounts save(Taxpayer_mail_accounts taxpayer_mail_accounts) {
        log.debug("Request to save Taxpayer_mail_accounts : {}", taxpayer_mail_accounts);
        Taxpayer_mail_accounts result = taxpayer_mail_accountsRepository.save(taxpayer_mail_accounts);
        return result;
    }

    /**
     *  Get all the taxpayer_mail_accounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_mail_accounts> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_mail_accounts");
        Page<Taxpayer_mail_accounts> result = taxpayer_mail_accountsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_mail_accounts by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_mail_accounts findOne(Long id) {
        log.debug("Request to get Taxpayer_mail_accounts : {}", id);
        Taxpayer_mail_accounts taxpayer_mail_accounts = taxpayer_mail_accountsRepository.findOne(id);
        return taxpayer_mail_accounts;
    }

    /**
     *  Delete the  taxpayer_mail_accounts by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_mail_accounts : {}", id);
        taxpayer_mail_accountsRepository.delete(id);
    }
}