package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_kind_payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_kind_payment.
 */
public interface Com_kind_paymentService {

    /**
     * Save a com_kind_payment.
     * 
     * @param com_kind_payment the entity to save
     * @return the persisted entity
     */
    Com_kind_payment save(Com_kind_payment com_kind_payment);

    /**
     *  Get all the com_kind_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_kind_payment> findAll(Pageable pageable);

    /**
     *  Get the "id" com_kind_payment.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_kind_payment findOne(Long id);

    /**
     *  Delete the "id" com_kind_payment.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
