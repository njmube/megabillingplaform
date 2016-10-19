package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Taxpayer_requestService;
import org.megapractical.billingplatform.domain.Taxpayer_request;
import org.megapractical.billingplatform.repository.Taxpayer_requestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Taxpayer_request.
 */
@Service
@Transactional
public class Taxpayer_requestServiceImpl implements Taxpayer_requestService{

    private final Logger log = LoggerFactory.getLogger(Taxpayer_requestServiceImpl.class);
    
    @Inject
    private Taxpayer_requestRepository taxpayer_requestRepository;
    
    /**
     * Save a taxpayer_request.
     * 
     * @param taxpayer_request the entity to save
     * @return the persisted entity
     */
    public Taxpayer_request save(Taxpayer_request taxpayer_request) {
        log.debug("Request to save Taxpayer_request : {}", taxpayer_request);
        Taxpayer_request result = taxpayer_requestRepository.save(taxpayer_request);
        return result;
    }

    /**
     *  Get all the taxpayer_requests.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Taxpayer_request> findAll(Pageable pageable) {
        log.debug("Request to get all Taxpayer_requests");
        Page<Taxpayer_request> result = taxpayer_requestRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one taxpayer_request by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Taxpayer_request findOne(Long id) {
        log.debug("Request to get Taxpayer_request : {}", id);
        Taxpayer_request taxpayer_request = taxpayer_requestRepository.findOne(id);
        return taxpayer_request;
    }

    /**
     *  Delete the  taxpayer_request by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Taxpayer_request : {}", id);
        taxpayer_requestRepository.delete(id);
    }
}
