package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_ftp_account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_ftp_account.
 */
public interface Taxpayer_ftp_accountService {

    /**
     * Save a taxpayer_ftp_account.
     * 
     * @param taxpayer_ftp_account the entity to save
     * @return the persisted entity
     */
    Taxpayer_ftp_account save(Taxpayer_ftp_account taxpayer_ftp_account);

    /**
     *  Get all the taxpayer_ftp_accounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_ftp_account> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_ftp_account.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_ftp_account findOne(Long id);

    /**
     *  Delete the "id" taxpayer_ftp_account.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
