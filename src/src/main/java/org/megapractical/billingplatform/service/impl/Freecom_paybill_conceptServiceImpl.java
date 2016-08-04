package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_paybill_conceptService;
import org.megapractical.billingplatform.domain.Freecom_paybill_concept;
import org.megapractical.billingplatform.repository.Freecom_paybill_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_paybill_concept.
 */
@Service
@Transactional
public class Freecom_paybill_conceptServiceImpl implements Freecom_paybill_conceptService{

    private final Logger log = LoggerFactory.getLogger(Freecom_paybill_conceptServiceImpl.class);
    
    @Inject
    private Freecom_paybill_conceptRepository freecom_paybill_conceptRepository;
    
    /**
     * Save a freecom_paybill_concept.
     * 
     * @param freecom_paybill_concept the entity to save
     * @return the persisted entity
     */
    public Freecom_paybill_concept save(Freecom_paybill_concept freecom_paybill_concept) {
        log.debug("Request to save Freecom_paybill_concept : {}", freecom_paybill_concept);
        Freecom_paybill_concept result = freecom_paybill_conceptRepository.save(freecom_paybill_concept);
        return result;
    }

    /**
     *  Get all the freecom_paybill_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_paybill_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_paybill_concepts");
        Page<Freecom_paybill_concept> result = freecom_paybill_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_paybill_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_paybill_concept findOne(Long id) {
        log.debug("Request to get Freecom_paybill_concept : {}", id);
        Freecom_paybill_concept freecom_paybill_concept = freecom_paybill_conceptRepository.findOne(id);
        return freecom_paybill_concept;
    }

    /**
     *  Delete the  freecom_paybill_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_paybill_concept : {}", id);
        freecom_paybill_conceptRepository.delete(id);
    }
}
