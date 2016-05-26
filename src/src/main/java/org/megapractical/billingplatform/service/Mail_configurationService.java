package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Mail_configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Mail_configuration.
 */
public interface Mail_configurationService {

    /**
     * Save a mail_configuration.
     * 
     * @param mail_configuration the entity to save
     * @return the persisted entity
     */
    Mail_configuration save(Mail_configuration mail_configuration);

    /**
     *  Get all the mail_configurations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Mail_configuration> findAll(Pageable pageable);

    /**
     *  Get the "id" mail_configuration.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Mail_configuration findOne(Long id);

    /**
     *  Delete the "id" mail_configuration.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
