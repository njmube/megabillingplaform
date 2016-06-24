package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_cfdiService;
import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.repository.Free_cfdiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_cfdi.
 */
@Service
@Transactional
public class Free_cfdiServiceImpl implements Free_cfdiService{

    private final Logger log = LoggerFactory.getLogger(Free_cfdiServiceImpl.class);
    
    @Inject
    private Free_cfdiRepository free_cfdiRepository;
    
    /**
     * Save a free_cfdi.
     * 
     * @param free_cfdi the entity to save
     * @return the persisted entity
     */
    public Free_cfdi save(Free_cfdi free_cfdi) {
        log.debug("Request to save Free_cfdi : {}", free_cfdi);
        Free_cfdi result = free_cfdiRepository.save(free_cfdi);
        return result;
    }

    /**
     *  Get all the free_cfdis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Free_cfdis");
        Page<Free_cfdi> result = free_cfdiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_cfdi findOne(Long id) {
        log.debug("Request to get Free_cfdi : {}", id);
        Free_cfdi free_cfdi = free_cfdiRepository.findOne(id);
        return free_cfdi;
    }

    /**
     *  Delete the  free_cfdi by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_cfdi : {}", id);
        free_cfdiRepository.delete(id);
    }
}
