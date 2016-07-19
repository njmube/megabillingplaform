package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Payment_method;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Payment_method.
 */
public interface Payment_methodService {

    /**
     * Save a payment_method.
     *
     * @param payment_method the entity to save
     * @return the persisted entity
     */
    Payment_method save(Payment_method payment_method);

    /**
     *  Get all the payment_methods.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Payment_method> findAll(Pageable pageable);

    Page<Payment_method> findAllByNameAndCode(String filtername, String filtercode, Pageable pageable);

    /**
     *  Get the "id" payment_method.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Payment_method findOne(Long id);

    /**
     *  Delete the "id" payment_method.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
