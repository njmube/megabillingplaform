package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Request_taxpayer_account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Request_taxpayer_account.
 */
public interface Request_taxpayer_accountService {

    /**
     * Save a request_taxpayer_account.
     *
     * @param request_taxpayer_account the entity to save
     * @return the persisted entity
     */
    Request_taxpayer_account save(Request_taxpayer_account request_taxpayer_account);

    /**
     *  Get all the request_taxpayer_accounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Request_taxpayer_account> findAll(Pageable pageable);

    //Page<Request_taxpayer_account> findAllOrderByIdDesc(Pageable pageable);

    /**
     *  Get the "id" request_taxpayer_account.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Request_taxpayer_account findOne(Long id);

    /**
     *  Delete the "id" request_taxpayer_account.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
