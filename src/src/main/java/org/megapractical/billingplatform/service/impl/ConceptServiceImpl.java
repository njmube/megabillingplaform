package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.ConceptService;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.repository.ConceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Concept.
 */
@Service
@Transactional
public class ConceptServiceImpl implements ConceptService{

    private final Logger log = LoggerFactory.getLogger(ConceptServiceImpl.class);
    
    @Inject
    private ConceptRepository conceptRepository;
    
    /**
     * Save a concept.
     * 
     * @param concept the entity to save
     * @return the persisted entity
     */
    public Concept save(Concept concept) {
        log.debug("Request to save Concept : {}", concept);
        Concept result = conceptRepository.save(concept);
        return result;
    }

    /**
     *  Get all the concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Concept> findAll(Pageable pageable) {
        log.debug("Request to get all Concepts");
        Page<Concept> result = conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Concept findOne(Long id) {
        log.debug("Request to get Concept : {}", id);
        Concept concept = conceptRepository.findOne(id);
        return concept;
    }

    /**
     *  Delete the  concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Concept : {}", id);
        conceptRepository.delete(id);
    }
}
