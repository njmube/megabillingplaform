package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_kind_paymentService;
import org.megapractical.billingplatform.domain.Com_kind_payment;
import org.megapractical.billingplatform.repository.Com_kind_paymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_kind_payment.
 */
@Service
@Transactional
public class Com_kind_paymentServiceImpl implements Com_kind_paymentService{

    private final Logger log = LoggerFactory.getLogger(Com_kind_paymentServiceImpl.class);
    
    @Inject
    private Com_kind_paymentRepository com_kind_paymentRepository;
    
    /**
     * Save a com_kind_payment.
     * 
     * @param com_kind_payment the entity to save
     * @return the persisted entity
     */
    public Com_kind_payment save(Com_kind_payment com_kind_payment) {
        log.debug("Request to save Com_kind_payment : {}", com_kind_payment);
        Com_kind_payment result = com_kind_paymentRepository.save(com_kind_payment);
        return result;
    }

    /**
     *  Get all the com_kind_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_kind_payment> findAll(Pageable pageable) {
        log.debug("Request to get all Com_kind_payments");
        Page<Com_kind_payment> result = com_kind_paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_kind_payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_kind_payment findOne(Long id) {
        log.debug("Request to get Com_kind_payment : {}", id);
        Com_kind_payment com_kind_payment = com_kind_paymentRepository.findOne(id);
        return com_kind_payment;
    }

    /**
     *  Delete the  com_kind_payment by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_kind_payment : {}", id);
        com_kind_paymentRepository.delete(id);
    }
}
