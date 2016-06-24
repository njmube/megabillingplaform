package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_digital_certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_digital_certificate.
 */
public interface Free_digital_certificateService {

    /**
     * Save a free_digital_certificate.
     * 
     * @param free_digital_certificate the entity to save
     * @return the persisted entity
     */
    Free_digital_certificate save(Free_digital_certificate free_digital_certificate);

    /**
     *  Get all the free_digital_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_digital_certificate> findAll(Pageable pageable);

    /**
     *  Get the "id" free_digital_certificate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_digital_certificate findOne(Long id);

    /**
     *  Delete the "id" free_digital_certificate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
