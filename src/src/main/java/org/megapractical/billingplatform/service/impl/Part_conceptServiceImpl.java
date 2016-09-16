package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Part_conceptService;
import org.megapractical.billingplatform.domain.Part_concept;
import org.megapractical.billingplatform.repository.Part_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Part_concept.
 */
@Service
@Transactional
public class Part_conceptServiceImpl implements Part_conceptService{

    private final Logger log = LoggerFactory.getLogger(Part_conceptServiceImpl.class);
    
    @Inject
    private Part_conceptRepository part_conceptRepository;
    
    /**
     * Save a part_concept.
     * 
     * @param part_concept the entity to save
     * @return the persisted entity
     */
    public Part_concept save(Part_concept part_concept) {
        log.debug("Request to save Part_concept : {}", part_concept);
        Part_concept result = part_conceptRepository.save(part_concept);
        return result;
    }

    /**
     *  Get all the part_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Part_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Part_concepts");
        Page<Part_concept> result = part_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one part_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Part_concept findOne(Long id) {
        log.debug("Request to get Part_concept : {}", id);
        Part_concept part_concept = part_conceptRepository.findOne(id);
        return part_concept;
    }

    /**
     *  Delete the  part_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Part_concept : {}", id);
        part_conceptRepository.delete(id);
    }
}
