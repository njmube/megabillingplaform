package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_tax_retentionsService;
import org.megapractical.billingplatform.domain.Free_tax_retentions;
import org.megapractical.billingplatform.repository.Free_tax_retentionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_tax_retentions.
 */
@Service
@Transactional
public class Free_tax_retentionsServiceImpl implements Free_tax_retentionsService{

    private final Logger log = LoggerFactory.getLogger(Free_tax_retentionsServiceImpl.class);
    
    @Inject
    private Free_tax_retentionsRepository free_tax_retentionsRepository;
    
    /**
     * Save a free_tax_retentions.
     * 
     * @param free_tax_retentions the entity to save
     * @return the persisted entity
     */
    public Free_tax_retentions save(Free_tax_retentions free_tax_retentions) {
        log.debug("Request to save Free_tax_retentions : {}", free_tax_retentions);
        Free_tax_retentions result = free_tax_retentionsRepository.save(free_tax_retentions);
        return result;
    }

    /**
     *  Get all the free_tax_retentions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_tax_retentions> findAll(Pageable pageable) {
        log.debug("Request to get all Free_tax_retentions");
        Page<Free_tax_retentions> result = free_tax_retentionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_tax_retentions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_tax_retentions findOne(Long id) {
        log.debug("Request to get Free_tax_retentions : {}", id);
        Free_tax_retentions free_tax_retentions = free_tax_retentionsRepository.findOne(id);
        return free_tax_retentions;
    }

    /**
     *  Delete the  free_tax_retentions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_tax_retentions : {}", id);
        free_tax_retentionsRepository.delete(id);
    }
}
