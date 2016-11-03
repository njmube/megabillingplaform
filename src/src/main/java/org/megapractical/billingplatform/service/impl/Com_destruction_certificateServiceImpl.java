package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_destruction_certificateService;
import org.megapractical.billingplatform.domain.Com_destruction_certificate;
import org.megapractical.billingplatform.repository.Com_destruction_certificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_destruction_certificate.
 */
@Service
@Transactional
public class Com_destruction_certificateServiceImpl implements Com_destruction_certificateService{

    private final Logger log = LoggerFactory.getLogger(Com_destruction_certificateServiceImpl.class);
    
    @Inject
    private Com_destruction_certificateRepository com_destruction_certificateRepository;
    
    /**
     * Save a com_destruction_certificate.
     * 
     * @param com_destruction_certificate the entity to save
     * @return the persisted entity
     */
    public Com_destruction_certificate save(Com_destruction_certificate com_destruction_certificate) {
        log.debug("Request to save Com_destruction_certificate : {}", com_destruction_certificate);
        Com_destruction_certificate result = com_destruction_certificateRepository.save(com_destruction_certificate);
        return result;
    }

    /**
     *  Get all the com_destruction_certificates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_destruction_certificate> findAll(Pageable pageable) {
        log.debug("Request to get all Com_destruction_certificates");
        Page<Com_destruction_certificate> result = com_destruction_certificateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_destruction_certificate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_destruction_certificate findOne(Long id) {
        log.debug("Request to get Com_destruction_certificate : {}", id);
        Com_destruction_certificate com_destruction_certificate = com_destruction_certificateRepository.findOne(id);
        return com_destruction_certificate;
    }

    /**
     *  Delete the  com_destruction_certificate by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_destruction_certificate : {}", id);
        com_destruction_certificateRepository.delete(id);
    }
}
