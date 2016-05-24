package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Package_transactionsService;
import org.megapractical.billingplatform.domain.Package_transactions;
import org.megapractical.billingplatform.repository.Package_transactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Package_transactions.
 */
@Service
@Transactional
public class Package_transactionsServiceImpl implements Package_transactionsService{

    private final Logger log = LoggerFactory.getLogger(Package_transactionsServiceImpl.class);
    
    @Inject
    private Package_transactionsRepository package_transactionsRepository;
    
    /**
     * Save a package_transactions.
     * 
     * @param package_transactions the entity to save
     * @return the persisted entity
     */
    public Package_transactions save(Package_transactions package_transactions) {
        log.debug("Request to save Package_transactions : {}", package_transactions);
        Package_transactions result = package_transactionsRepository.save(package_transactions);
        return result;
    }

    /**
     *  Get all the package_transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Package_transactions> findAll(Pageable pageable) {
        log.debug("Request to get all Package_transactions");
        Page<Package_transactions> result = package_transactionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one package_transactions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Package_transactions findOne(Long id) {
        log.debug("Request to get Package_transactions : {}", id);
        Package_transactions package_transactions = package_transactionsRepository.findOne(id);
        return package_transactions;
    }

    /**
     *  Delete the  package_transactions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Package_transactions : {}", id);
        package_transactionsRepository.delete(id);
    }
}
