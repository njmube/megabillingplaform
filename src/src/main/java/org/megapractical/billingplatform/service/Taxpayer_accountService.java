package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_account;
import org.megapractical.billingplatform.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_account.
 */
public interface Taxpayer_accountService {

    /**
     * Save a taxpayer_account.
     *
     * @param taxpayer_account the entity to save
     * @return the persisted entity
     */
    Taxpayer_account save(Taxpayer_account taxpayer_account);

    /**
     *  Get all the taxpayer_accounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_account> findAll(Pageable pageable);


    Page<Taxpayer_account> findCustom(User user,Pageable pageable);

    List<Taxpayer_account> findCustomList(User user);

    /**
     *  Get the "id" taxpayer_account.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_account findOne(Long id);

    /**
     *  Delete the "id" taxpayer_account.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
