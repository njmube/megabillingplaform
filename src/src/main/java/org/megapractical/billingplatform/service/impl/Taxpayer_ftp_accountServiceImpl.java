package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_ftp_accountService;
import org.megapractical.billingplatform.domain.Taxpayer_ftp_account;
import org.megapractical.billingplatform.repository.Taxpayer_ftp_accountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_ftp_account.
 */
@Service
@Transactional
public class Taxpayer_ftp_accountServiceImpl implements Taxpayer_ftp_accountService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_ftp_accountServiceImpl.class);
    
    @Inject
    private Taxpayer_ftp_accountRepository taxpayer_ftp_accountRepository;
    
    /**
     * Save a taxpayer_ftp_account.
     * 
     * @param taxpayer_ftp_account the entity to save
     * @return the persisted entity
     */
    public Taxpayer_ftp_account save(Taxpayer_ftp_account taxpayer_ftp_account) {
        log.debug("Request to save Taxpayer_ftp_account : {}", taxpayer_ftp_account);
        Taxpayer_ftp_account result = taxpayer_ftp_accountRepository.save(taxpayer_ftp_account);
        return result;
    }

    /**
     *  Get all the taxpayer_ftp_accounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_ftp_account> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_ftp_accounts");
        Page<Taxpayer_ftp_account> result = taxpayer_ftp_accountRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_ftp_account by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_ftp_account findOne(Long id) {
        log.debug("Request to get Taxpayer_ftp_account : {}", id);
        Taxpayer_ftp_account taxpayer_ftp_account = taxpayer_ftp_accountRepository.findOne(id);
        return taxpayer_ftp_account;
    }

    /**
     *  Delete the  taxpayer_ftp_account by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_ftp_account : {}", id);
        taxpayer_ftp_accountRepository.delete(id);
    }
}
