package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Way_payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Way_payment.
 */
public interface Way_paymentService {

    /**
     * Save a way_payment.
     *
     * @param way_payment the entity to save
     * @return the persisted entity
     */
    Way_payment save(Way_payment way_payment);

    /**
     *  Get all the way_payments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Way_payment> findAll(Pageable pageable);

    Page<Way_payment> findAllByName(String filtername, Pageable pageable);

    /**
     *  Get the "id" way_payment.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Way_payment findOne(Long id);

    /**
     *  Delete the "id" way_payment.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
