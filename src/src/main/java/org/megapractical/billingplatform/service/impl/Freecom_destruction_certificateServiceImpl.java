package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_destruction_certificateService;
import org.megapractical.billingplatform.domain.Freecom_destruction_certificate;
import org.megapractical.billingplatform.repository.Freecom_destruction_certificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_destruction_certificate.
 */
@Service
@Transactional
public class Freecom_destruction_certificateServiceImpl implements Freecom_destruction_certificateService{

    private final Logger log = LoggerFactory.getLogger(Freecom_destruction_certificateServiceImpl.class);
    
    @Inject
    private Freecom_destruction_certificateRepository freecom_destruction_certificateRepository;
    
    /**
     * Save a freecom_destruction_certificate.
     * 
     * @param freecom_destruction_certificate the entity to save
     * @return the persisted entity
     */
    public Freecom_destruction_certificate save(Freecom_destruction_certificate freecom_destruction_certificate) {
        log.debug("Request to save Freecom_destruction_certificate : {}", freecom_destruction_certificate);
        Freecom_destruction_certificate result = freecom_destruction_certificateRepository.save(freecom_destruction_certificate);
        return result;
    }

    /**
     *  Get all the freecom_destruction_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_destruction_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_destruction_certificates");
        Page<Freecom_destruction_certificate> result = freecom_destruction_certificateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_destruction_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_destruction_certificate findOne(Long id) {
        log.debug("Request to get Freecom_destruction_certificate : {}", id);
        Freecom_destruction_certificate freecom_destruction_certificate = freecom_destruction_certificateRepository.findOne(id);
        return freecom_destruction_certificate;
    }

    /**
     *  Delete the  freecom_destruction_certificate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_destruction_certificate : {}", id);
        freecom_destruction_certificateRepository.delete(id);
    }
}
