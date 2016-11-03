package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_destruction_certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_destruction_certificate.
 */
public interface Com_destruction_certificateService {

    /**
     * Save a com_destruction_certificate.
     * 
     * @param com_destruction_certificate the entity to save
     * @return the persisted entity
     */
    Com_destruction_certificate save(Com_destruction_certificate com_destruction_certificate);

    /**
     *  Get all the com_destruction_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_destruction_certificate> findAll(Pageable pageable);

    /**
     *  Get the "id" com_destruction_certificate.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_destruction_certificate findOne(Long id);

    /**
     *  Delete the "id" com_destruction_certificate.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
