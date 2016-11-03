package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_ecc_11_conceptService;
import org.megapractical.billingplatform.domain.Com_ecc_11_concept;
import org.megapractical.billingplatform.repository.Com_ecc_11_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_ecc_11_concept.
 */
@Service
@Transactional
public class Com_ecc_11_conceptServiceImpl implements Com_ecc_11_conceptService{

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11_conceptServiceImpl.class);
    
    @Inject
    private Com_ecc_11_conceptRepository com_ecc_11_conceptRepository;
    
    /**
     * Save a com_ecc_11_concept.
     * 
     * @param com_ecc_11_concept the entity to save
     * @return the persisted entity
     */
    public Com_ecc_11_concept save(Com_ecc_11_concept com_ecc_11_concept) {
        log.debug("Request to save Com_ecc_11_concept : {}", com_ecc_11_concept);
        Com_ecc_11_concept result = com_ecc_11_conceptRepository.save(com_ecc_11_concept);
        return result;
    }

    /**
     *  Get all the com_ecc_11_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_ecc_11_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Com_ecc_11_concepts");
        Page<Com_ecc_11_concept> result = com_ecc_11_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_ecc_11_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_ecc_11_concept findOne(Long id) {
        log.debug("Request to get Com_ecc_11_concept : {}", id);
        Com_ecc_11_concept com_ecc_11_concept = com_ecc_11_conceptRepository.findOne(id);
        return com_ecc_11_concept;
    }

    /**
     *  Delete the  com_ecc_11_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_ecc_11_concept : {}", id);
        com_ecc_11_conceptRepository.delete(id);
    }
}
