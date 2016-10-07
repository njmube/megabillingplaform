package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_transactions;
import org.megapractical.billingplatform.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_transactions.
 */
public interface Taxpayer_transactionsService {

    /**
     * Save a taxpayer_transactions.
     *
     * @param taxpayer_transactions the entity to save
     * @return the persisted entity
     */
    Taxpayer_transactions save(Taxpayer_transactions taxpayer_transactions);

    /**
     *  Get all the taxpayer_transactions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_transactions> findAll(Pageable pageable);

    Page<Taxpayer_transactions> findByAccount(Integer idaccount, Pageable pageable);

    Page<Taxpayer_transactions> findByUser(User user, Pageable pageable);
    /**
     *  Get the "id" taxpayer_transactions.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_transactions findOne(Long id);

    /**
     *  Delete the "id" taxpayer_transactions.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
