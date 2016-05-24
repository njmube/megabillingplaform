package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Digital_certificateService;
import org.megapractical.billingplatform.domain.Digital_certificate;
import org.megapractical.billingplatform.repository.Digital_certificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Digital_certificate.
 */
@Service
@Transactional
public class Digital_certificateServiceImpl implements Digital_certificateService{

    private final Logger log = LoggerFactory.getLogger(Digital_certificateServiceImpl.class);
    
    @Inject
    private Digital_certificateRepository digital_certificateRepository;
    
    /**
     * Save a digital_certificate.
     * 
     * @param digital_certificate the entity to save
     * @return the persisted entity
     */
    public Digital_certificate save(Digital_certificate digital_certificate) {
        log.debug("Request to save Digital_certificate : {}", digital_certificate);
        Digital_certificate result = digital_certificateRepository.save(digital_certificate);
        return result;
    }

    /**
     *  Get all the digital_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Digital_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Digital_certificates");
        Page<Digital_certificate> result = digital_certificateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one digital_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Digital_certificate findOne(Long id) {
        log.debug("Request to get Digital_certificate : {}", id);
        Digital_certificate digital_certificate = digital_certificateRepository.findOne(id);
        return digital_certificate;
    }

    /**
     *  Delete the  digital_certificate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Digital_certificate : {}", id);
        digital_certificateRepository.delete(id);
    }
}
