package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_kind_paymentService;
import org.megapractical.billingplatform.domain.Freecom_kind_payment;
import org.megapractical.billingplatform.repository.Freecom_kind_paymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_kind_payment.
 */
@Service
@Transactional
public class Freecom_kind_paymentServiceImpl implements Freecom_kind_paymentService{

    private final Logger log = LoggerFactory.getLogger(Freecom_kind_paymentServiceImpl.class);
    
    @Inject
    private Freecom_kind_paymentRepository freecom_kind_paymentRepository;
    
    /**
     * Save a freecom_kind_payment.
     * 
     * @param freecom_kind_payment the entity to save
     * @return the persisted entity
     */
    public Freecom_kind_payment save(Freecom_kind_payment freecom_kind_payment) {
        log.debug("Request to save Freecom_kind_payment : {}", freecom_kind_payment);
        Freecom_kind_payment result = freecom_kind_paymentRepository.save(freecom_kind_payment);
        return result;
    }

    /**
     *  Get all the freecom_kind_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_kind_payment> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_kind_payments");
        Page<Freecom_kind_payment> result = freecom_kind_paymentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_kind_payment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_kind_payment findOne(Long id) {
        log.debug("Request to get Freecom_kind_payment : {}", id);
        Freecom_kind_payment freecom_kind_payment = freecom_kind_paymentRepository.findOne(id);
        return freecom_kind_payment;
    }

    /**
     *  Delete the  freecom_kind_payment by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_kind_payment : {}", id);
        freecom_kind_paymentRepository.delete(id);
    }
}
