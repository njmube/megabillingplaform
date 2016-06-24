package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_conceptService;
import org.megapractical.billingplatform.domain.Free_concept;
import org.megapractical.billingplatform.repository.Free_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_concept.
 */
@Service
@Transactional
public class Free_conceptServiceImpl implements Free_conceptService{

    private final Logger log = LoggerFactory.getLogger(Free_conceptServiceImpl.class);
    
    @Inject
    private Free_conceptRepository free_conceptRepository;
    
    /**
     * Save a free_concept.
     * 
     * @param free_concept the entity to save
     * @return the persisted entity
     */
    public Free_concept save(Free_concept free_concept) {
        log.debug("Request to save Free_concept : {}", free_concept);
        Free_concept result = free_conceptRepository.save(free_concept);
        return result;
    }

    /**
     *  Get all the free_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Free_concepts");
        Page<Free_concept> result = free_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_concept findOne(Long id) {
        log.debug("Request to get Free_concept : {}", id);
        Free_concept free_concept = free_conceptRepository.findOne(id);
        return free_concept;
    }

    /**
     *  Delete the  free_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_concept : {}", id);
        free_conceptRepository.delete(id);
    }
}
