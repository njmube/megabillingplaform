package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Way_paymentService;
import org.megapractical.billingplatform.domain.Way_payment;
import org.megapractical.billingplatform.repository.Way_paymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Way_payment.
 */
@Service
@Transactional
public class Way_paymentServiceImpl implements Way_paymentService{

    private final Logger log = LoggerFactory.getLogger(Way_paymentServiceImpl.class);
    
    @Inject
    private Way_paymentRepository way_paymentRepository;
    
    /**
     * Save a way_payment.
     * 
     * @param way_payment the entity to save
     * @return the persisted entity
     */
    public Way_payment save(Way_payment way_payment) {
        log.debug("Request to save Way_payment : {}", way_payment);
        Way_payment result = way_paymentRepository.save(way_payment);
        return result;
    }

    /**
     *  Get all the way_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Way_payment> findAll(Pageable pageable) {
        log.debug("Request to get all Way_payments");
        Page<Way_payment> result = way_paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one way_payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Way_payment findOne(Long id) {
        log.debug("Request to get Way_payment : {}", id);
        Way_payment way_payment = way_paymentRepository.findOne(id);
        return way_payment;
    }

    /**
     *  Delete the  way_payment by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Way_payment : {}", id);
        way_paymentRepository.delete(id);
    }
}
