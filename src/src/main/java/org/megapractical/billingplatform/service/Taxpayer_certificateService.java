package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Taxpayer_certificate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Taxpayer_certificate.
 */
public interface Taxpayer_certificateService {

    /**
     * Save a taxpayer_certificate.
     *
     * @param taxpayer_certificate the entity to save
     * @return the persisted entity
     */
    Taxpayer_certificate save(Taxpayer_certificate taxpayer_certificate, String rfc);

    String[] validateCertificate(byte[] cert, byte[]key, String pass);

    Taxpayer_certificate InfoCertificate(Taxpayer_certificate taxpayer_certificate);

    /**
     *  Get all the taxpayer_certificates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Taxpayer_certificate> findAll(Pageable pageable);

    /**
     *  Get the "id" taxpayer_certificate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Taxpayer_certificate findOne(Long id);

    /**
     *  Delete the "id" taxpayer_certificate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
