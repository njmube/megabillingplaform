package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Tax_conceptService;
import org.megapractical.billingplatform.domain.Tax_concept;
import org.megapractical.billingplatform.repository.Tax_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Tax_concept.
 */
@Service
@Transactional
public class Tax_conceptServiceImpl implements Tax_conceptService{

    private final Logger log = LoggerFactory.getLogger(Tax_conceptServiceImpl.class);
    
    @Inject
    private Tax_conceptRepository tax_conceptRepository;
    
    /**
     * Save a tax_concept.
     * 
     * @param tax_concept the entity to save
     * @return the persisted entity
     */
    public Tax_concept save(Tax_concept tax_concept) {
        log.debug("Request to save Tax_concept : {}", tax_concept);
        Tax_concept result = tax_conceptRepository.save(tax_concept);
        return result;
    }

    /**
     *  Get all the tax_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Tax_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Tax_concepts");
        Page<Tax_concept> result = tax_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one tax_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Tax_concept findOne(Long id) {
        log.debug("Request to get Tax_concept : {}", id);
        Tax_concept tax_concept = tax_conceptRepository.findOne(id);
        return tax_concept;
    }

    /**
     *  Delete the  tax_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tax_concept : {}", id);
        tax_conceptRepository.delete(id);
    }
}
