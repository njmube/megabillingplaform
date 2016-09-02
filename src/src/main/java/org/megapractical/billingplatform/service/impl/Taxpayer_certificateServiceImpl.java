package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_certificateService;
import org.megapractical.billingplatform.domain.Taxpayer_certificate;
import org.megapractical.billingplatform.repository.Taxpayer_certificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_certificate.
 */
@Service
@Transactional
public class Taxpayer_certificateServiceImpl implements Taxpayer_certificateService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_certificateServiceImpl.class);
    
    @Inject
    private Taxpayer_certificateRepository taxpayer_certificateRepository;
    
    /**
     * Save a taxpayer_certificate.
     * 
     * @param taxpayer_certificate the entity to save
     * @return the persisted entity
     */
    public Taxpayer_certificate save(Taxpayer_certificate taxpayer_certificate) {
        log.debug("Request to save Taxpayer_certificate : {}", taxpayer_certificate);
        Taxpayer_certificate result = taxpayer_certificateRepository.save(taxpayer_certificate);
        return result;
    }

    /**
     *  Get all the taxpayer_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_certificates");
        Page<Taxpayer_certificate> result = taxpayer_certificateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_certificate findOne(Long id) {
        log.debug("Request to get Taxpayer_certificate : {}", id);
        Taxpayer_certificate taxpayer_certificate = taxpayer_certificateRepository.findOne(id);
        return taxpayer_certificate;
    }

    /**
     *  Delete the  taxpayer_certificate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_certificate : {}", id);
        taxpayer_certificateRepository.delete(id);
    }
}
