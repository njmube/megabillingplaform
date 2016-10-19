package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_request_confirmService;
import org.megapractical.billingplatform.domain.Taxpayer_request_confirm;
import org.megapractical.billingplatform.repository.Taxpayer_request_confirmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_request_confirm.
 */
@Service
@Transactional
public class Taxpayer_request_confirmServiceImpl implements Taxpayer_request_confirmService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_request_confirmServiceImpl.class);
    
    @Inject
    private Taxpayer_request_confirmRepository taxpayer_request_confirmRepository;
    
    /**
     * Save a taxpayer_request_confirm.
     * 
     * @param taxpayer_request_confirm the entity to save
     * @return the persisted entity
     */
    public Taxpayer_request_confirm save(Taxpayer_request_confirm taxpayer_request_confirm) {
        log.debug("Request to save Taxpayer_request_confirm : {}", taxpayer_request_confirm);
        Taxpayer_request_confirm result = taxpayer_request_confirmRepository.save(taxpayer_request_confirm);
        return result;
    }

    /**
     *  Get all the taxpayer_request_confirms.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_request_confirm> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_request_confirms");
        Page<Taxpayer_request_confirm> result = taxpayer_request_confirmRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_request_confirm by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_request_confirm findOne(Long id) {
        log.debug("Request to get Taxpayer_request_confirm : {}", id);
        Taxpayer_request_confirm taxpayer_request_confirm = taxpayer_request_confirmRepository.findOne(id);
        return taxpayer_request_confirm;
    }

    /**
     *  Delete the  taxpayer_request_confirm by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_request_confirm : {}", id);
        taxpayer_request_confirmRepository.delete(id);
    }
}
