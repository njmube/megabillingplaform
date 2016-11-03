package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_paybill_conceptService;
import org.megapractical.billingplatform.domain.Com_paybill_concept;
import org.megapractical.billingplatform.repository.Com_paybill_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_paybill_concept.
 */
@Service
@Transactional
public class Com_paybill_conceptServiceImpl implements Com_paybill_conceptService{

    private final Logger log = LoggerFactory.getLogger(Com_paybill_conceptServiceImpl.class);
    
    @Inject
    private Com_paybill_conceptRepository com_paybill_conceptRepository;
    
    /**
     * Save a com_paybill_concept.
     * 
     * @param com_paybill_concept the entity to save
     * @return the persisted entity
     */
    public Com_paybill_concept save(Com_paybill_concept com_paybill_concept) {
        log.debug("Request to save Com_paybill_concept : {}", com_paybill_concept);
        Com_paybill_concept result = com_paybill_conceptRepository.save(com_paybill_concept);
        return result;
    }

    /**
     *  Get all the com_paybill_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_paybill_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Com_paybill_concepts");
        Page<Com_paybill_concept> result = com_paybill_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_paybill_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_paybill_concept findOne(Long id) {
        log.debug("Request to get Com_paybill_concept : {}", id);
        Com_paybill_concept com_paybill_concept = com_paybill_conceptRepository.findOne(id);
        return com_paybill_concept;
    }

    /**
     *  Delete the  com_paybill_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_paybill_concept : {}", id);
        com_paybill_conceptRepository.delete(id);
    }
}
