package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_tax_transferedService;
import org.megapractical.billingplatform.domain.Free_tax_transfered;
import org.megapractical.billingplatform.repository.Free_tax_transferedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_tax_transfered.
 */
@Service
@Transactional
public class Free_tax_transferedServiceImpl implements Free_tax_transferedService{

    private final Logger log = LoggerFactory.getLogger(Free_tax_transferedServiceImpl.class);
    
    @Inject
    private Free_tax_transferedRepository free_tax_transferedRepository;
    
    /**
     * Save a free_tax_transfered.
     * 
     * @param free_tax_transfered the entity to save
     * @return the persisted entity
     */
    public Free_tax_transfered save(Free_tax_transfered free_tax_transfered) {
        log.debug("Request to save Free_tax_transfered : {}", free_tax_transfered);
        Free_tax_transfered result = free_tax_transferedRepository.save(free_tax_transfered);
        return result;
    }

    /**
     *  Get all the free_tax_transfereds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_tax_transfered> findAll(Pageable pageable) {
        log.debug("Request to get all Free_tax_transfereds");
        Page<Free_tax_transfered> result = free_tax_transferedRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_tax_transfered by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_tax_transfered findOne(Long id) {
        log.debug("Request to get Free_tax_transfered : {}", id);
        Free_tax_transfered free_tax_transfered = free_tax_transferedRepository.findOne(id);
        return free_tax_transfered;
    }

    /**
     *  Delete the  free_tax_transfered by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_tax_transfered : {}", id);
        free_tax_transferedRepository.delete(id);
    }
}
