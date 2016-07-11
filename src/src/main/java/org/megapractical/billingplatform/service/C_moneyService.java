package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_money.
 */
public interface C_moneyService {

    /**
     * Save a c_money.
     *
     * @param c_money the entity to save
     * @return the persisted entity
     */
    C_money save(C_money c_money);

    /**
     *  Get all the c_monies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_money> findAll(Pageable pageable);

    /**
     *  Get all the c_monies.
     *
     *  @return the list of entities
     */
    List<C_money> findAll();

    /**
     *  Get the "id" c_money.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    C_money findOne(Long id);

    /**
     *  Delete the "id" c_money.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
