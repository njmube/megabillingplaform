package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_digital_certificateService;
import org.megapractical.billingplatform.domain.Free_digital_certificate;
import org.megapractical.billingplatform.repository.Free_digital_certificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_digital_certificate.
 */
@Service
@Transactional
public class Free_digital_certificateServiceImpl implements Free_digital_certificateService{

    private final Logger log = LoggerFactory.getLogger(Free_digital_certificateServiceImpl.class);
    
    @Inject
    private Free_digital_certificateRepository free_digital_certificateRepository;
    
    /**
     * Save a free_digital_certificate.
     * 
     * @param free_digital_certificate the entity to save
     * @return the persisted entity
     */
    public Free_digital_certificate save(Free_digital_certificate free_digital_certificate) {
        log.debug("Request to save Free_digital_certificate : {}", free_digital_certificate);
        Free_digital_certificate result = free_digital_certificateRepository.save(free_digital_certificate);
        return result;
    }

    /**
     *  Get all the free_digital_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_digital_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Free_digital_certificates");
        Page<Free_digital_certificate> result = free_digital_certificateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_digital_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_digital_certificate findOne(Long id) {
        log.debug("Request to get Free_digital_certificate : {}", id);
        Free_digital_certificate free_digital_certificate = free_digital_certificateRepository.findOne(id);
        return free_digital_certificate;
    }

    /**
     *  Delete the  free_digital_certificate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_digital_certificate : {}", id);
        free_digital_certificateRepository.delete(id);
    }
}
