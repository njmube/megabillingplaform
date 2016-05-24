package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Digital_certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Digital_certificate.
 */
public interface Digital_certificateService {

    /**
     * Save a digital_certificate.
     * 
     * @param digital_certificate the entity to save
     * @return the persisted entity
     */
    Digital_certificate save(Digital_certificate digital_certificate);

    /**
     *  Get all the digital_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Digital_certificate> findAll(Pageable pageable);

    /**
     *  Get the "id" digital_certificate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Digital_certificate findOne(Long id);

    /**
     *  Delete the "id" digital_certificate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
