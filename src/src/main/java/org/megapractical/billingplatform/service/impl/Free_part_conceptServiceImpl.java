package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_part_conceptService;
import org.megapractical.billingplatform.domain.Free_part_concept;
import org.megapractical.billingplatform.repository.Free_part_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_part_concept.
 */
@Service
@Transactional
public class Free_part_conceptServiceImpl implements Free_part_conceptService{

    private final Logger log = LoggerFactory.getLogger(Free_part_conceptServiceImpl.class);
    
    @Inject
    private Free_part_conceptRepository free_part_conceptRepository;
    
    /**
     * Save a free_part_concept.
     * 
     * @param free_part_concept the entity to save
     * @return the persisted entity
     */
    public Free_part_concept save(Free_part_concept free_part_concept) {
        log.debug("Request to save Free_part_concept : {}", free_part_concept);
        Free_part_concept result = free_part_conceptRepository.save(free_part_concept);
        return result;
    }

    /**
     *  Get all the free_part_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_part_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Free_part_concepts");
        Page<Free_part_concept> result = free_part_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_part_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_part_concept findOne(Long id) {
        log.debug("Request to get Free_part_concept : {}", id);
        Free_part_concept free_part_concept = free_part_conceptRepository.findOne(id);
        return free_part_concept;
    }

    /**
     *  Delete the  free_part_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_part_concept : {}", id);
        free_part_conceptRepository.delete(id);
    }
}
