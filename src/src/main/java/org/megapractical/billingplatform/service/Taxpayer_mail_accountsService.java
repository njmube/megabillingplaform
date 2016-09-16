package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_mail_accounts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_mail_accounts.
 */
public interface Taxpayer_mail_accountsService {

    /**
     * Save a taxpayer_mail_accounts.
     * 
     * @param taxpayer_mail_accounts the entity to save
     * @return the persisted entity
     */
    Taxpayer_mail_accounts save(Taxpayer_mail_accounts taxpayer_mail_accounts);

    /**
     *  Get all the taxpayer_mail_accounts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_mail_accounts> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_mail_accounts.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_mail_accounts findOne(Long id);

    /**
     *  Delete the "id" taxpayer_mail_accounts.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
