package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_ecc11_conceptService;
import org.megapractical.billingplatform.domain.Freecom_ecc11_concept;
import org.megapractical.billingplatform.repository.Freecom_ecc11_conceptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_ecc11_concept.
 */
@Service
@Transactional
public class Freecom_ecc11_conceptServiceImpl implements Freecom_ecc11_conceptService{

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11_conceptServiceImpl.class);
    
    @Inject
    private Freecom_ecc11_conceptRepository freecom_ecc11_conceptRepository;
    
    /**
     * Save a freecom_ecc11_concept.
     * 
     * @param freecom_ecc11_concept the entity to save
     * @return the persisted entity
     */
    public Freecom_ecc11_concept save(Freecom_ecc11_concept freecom_ecc11_concept) {
        log.debug("Request to save Freecom_ecc11_concept : {}", freecom_ecc11_concept);
        Freecom_ecc11_concept result = freecom_ecc11_conceptRepository.save(freecom_ecc11_concept);
        return result;
    }

    /**
     *  Get all the freecom_ecc11_concepts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_ecc11_concept> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_ecc11_concepts");
        Page<Freecom_ecc11_concept> result = freecom_ecc11_conceptRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_ecc11_concept by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_ecc11_concept findOne(Long id) {
        log.debug("Request to get Freecom_ecc11_concept : {}", id);
        Freecom_ecc11_concept freecom_ecc11_concept = freecom_ecc11_conceptRepository.findOne(id);
        return freecom_ecc11_concept;
    }

    /**
     *  Delete the  freecom_ecc11_concept by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_ecc11_concept : {}", id);
        freecom_ecc11_conceptRepository.delete(id);
    }
}
