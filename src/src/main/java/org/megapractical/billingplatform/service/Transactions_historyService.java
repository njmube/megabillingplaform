package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Transactions_history;
import org.megapractical.billingplatform.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Transactions_history.
 */
public interface Transactions_historyService {

    /**
     * Save a transactions_history.
     *
     * @param transactions_history the entity to save
     * @return the persisted entity
     */
    Transactions_history save(Transactions_history transactions_history);

    /**
     *  Get all the transactions_histories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Transactions_history> findAll(Pageable pageable);

    Page<Transactions_history> findByAccount(Integer idaccount, Integer month, Pageable pageable);

    Page<Transactions_history> findByUser(User user, Integer month, Pageable pageable);

    /**
     *  Get the "id" transactions_history.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Transactions_history findOne(Long id);

    /**
     *  Delete the "id" transactions_history.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
