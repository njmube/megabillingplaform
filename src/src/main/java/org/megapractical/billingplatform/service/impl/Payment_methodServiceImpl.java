package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Payment_methodService;
import org.megapractical.billingplatform.domain.Payment_method;
import org.megapractical.billingplatform.repository.Payment_methodRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Payment_method.
 */
@Service
@Transactional
public class Payment_methodServiceImpl implements Payment_methodService{

    private final Logger log = LoggerFactory.getLogger(Payment_methodServiceImpl.class);
    
    @Inject
    private Payment_methodRepository payment_methodRepository;
    
    /**
     * Save a payment_method.
     * 
     * @param payment_method the entity to save
     * @return the persisted entity
     */
    public Payment_method save(Payment_method payment_method) {
        log.debug("Request to save Payment_method : {}", payment_method);
        Payment_method result = payment_methodRepository.save(payment_method);
        return result;
    }

    /**
     *  Get all the payment_methods.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Payment_method> findAll(Pageable pageable) {
        log.debug("Request to get all Payment_methods");
        Page<Payment_method> result = payment_methodRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one payment_method by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Payment_method findOne(Long id) {
        log.debug("Request to get Payment_method : {}", id);
        Payment_method payment_method = payment_methodRepository.findOne(id);
        return payment_method;
    }

    /**
     *  Delete the  payment_method by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Payment_method : {}", id);
        payment_methodRepository.delete(id);
    }
}
