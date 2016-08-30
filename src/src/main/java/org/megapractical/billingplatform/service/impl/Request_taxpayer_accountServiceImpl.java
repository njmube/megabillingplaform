package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Request_taxpayer_accountService;
import org.megapractical.billingplatform.domain.Request_taxpayer_account;
import org.megapractical.billingplatform.repository.Request_taxpayer_accountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Request_taxpayer_account.
 */
@Service
@Transactional
public class Request_taxpayer_accountServiceImpl implements Request_taxpayer_accountService{

    private final Logger log = LoggerFactory.getLogger(Request_taxpayer_accountServiceImpl.class);
    
    @Inject
    private Request_taxpayer_accountRepository request_taxpayer_accountRepository;
    
    /**
     * Save a request_taxpayer_account.
     * 
     * @param request_taxpayer_account the entity to save
     * @return the persisted entity
     */
    public Request_taxpayer_account save(Request_taxpayer_account request_taxpayer_account) {
        log.debug("Request to save Request_taxpayer_account : {}", request_taxpayer_account);
        Request_taxpayer_account result = request_taxpayer_accountRepository.save(request_taxpayer_account);
        return result;
    }

    /**
     *  Get all the request_taxpayer_accounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Request_taxpayer_account> findAll(Pageable pageable) {
        log.debug("Request to get all Request_taxpayer_accounts");
        Page<Request_taxpayer_account> result = request_taxpayer_accountRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one request_taxpayer_account by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Request_taxpayer_account findOne(Long id) {
        log.debug("Request to get Request_taxpayer_account : {}", id);
        Request_taxpayer_account request_taxpayer_account = request_taxpayer_accountRepository.findOne(id);
        return request_taxpayer_account;
    }

    /**
     *  Delete the  request_taxpayer_account by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Request_taxpayer_account : {}", id);
        request_taxpayer_accountRepository.delete(id);
    }
}
