package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_destruction_certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_destruction_certificate.
 */
public interface Freecom_destruction_certificateService {

    /**
     * Save a freecom_destruction_certificate.
     * 
     * @param freecom_destruction_certificate the entity to save
     * @return the persisted entity
     */
    Freecom_destruction_certificate save(Freecom_destruction_certificate freecom_destruction_certificate);

    /**
     *  Get all the freecom_destruction_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_destruction_certificate> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_destruction_certificate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_destruction_certificate findOne(Long id);

    /**
     *  Delete the "id" freecom_destruction_certificate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
