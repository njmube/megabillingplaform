package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Type_transactionService;
import org.megapractical.billingplatform.domain.Type_transaction;
import org.megapractical.billingplatform.repository.Type_transactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Type_transaction.
 */
@Service
@Transactional
public class Type_transactionServiceImpl implements Type_transactionService{

    private final Logger log = LoggerFactory.getLogger(Type_transactionServiceImpl.class);
    
    @Inject
    private Type_transactionRepository type_transactionRepository;
    
    /**
     * Save a type_transaction.
     * 
     * @param type_transaction the entity to save
     * @return the persisted entity
     */
    public Type_transaction save(Type_transaction type_transaction) {
        log.debug("Request to save Type_transaction : {}", type_transaction);
        Type_transaction result = type_transactionRepository.save(type_transaction);
        return result;
    }

    /**
     *  Get all the type_transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Type_transaction> findAll(Pageable pageable) {
        log.debug("Request to get all Type_transactions");
        Page<Type_transaction> result = type_transactionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one type_transaction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Type_transaction findOne(Long id) {
        log.debug("Request to get Type_transaction : {}", id);
        Type_transaction type_transaction = type_transactionRepository.findOne(id);
        return type_transaction;
    }

    /**
     *  Delete the  type_transaction by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Type_transaction : {}", id);
        type_transactionRepository.delete(id);
    }
}
