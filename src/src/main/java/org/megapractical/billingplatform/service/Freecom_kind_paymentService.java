package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_kind_payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_kind_payment.
 */
public interface Freecom_kind_paymentService {

    /**
     * Save a freecom_kind_payment.
     * 
     * @param freecom_kind_payment the entity to save
     * @return the persisted entity
     */
    Freecom_kind_payment save(Freecom_kind_payment freecom_kind_payment);

    /**
     *  Get all the freecom_kind_payments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_kind_payment> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_kind_payment.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_kind_payment findOne(Long id);

    /**
     *  Delete the "id" freecom_kind_payment.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
