package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_accreditation_iepsService;
import org.megapractical.billingplatform.domain.Freecom_accreditation_ieps;
import org.megapractical.billingplatform.repository.Freecom_accreditation_iepsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_accreditation_ieps.
 */
@Service
@Transactional
public class Freecom_accreditation_iepsServiceImpl implements Freecom_accreditation_iepsService{

    private final Logger log = LoggerFactory.getLogger(Freecom_accreditation_iepsServiceImpl.class);
    
    @Inject
    private Freecom_accreditation_iepsRepository freecom_accreditation_iepsRepository;
    
    /**
     * Save a freecom_accreditation_ieps.
     * 
     * @param freecom_accreditation_ieps the entity to save
     * @return the persisted entity
     */
    public Freecom_accreditation_ieps save(Freecom_accreditation_ieps freecom_accreditation_ieps) {
        log.debug("Request to save Freecom_accreditation_ieps : {}", freecom_accreditation_ieps);
        Freecom_accreditation_ieps result = freecom_accreditation_iepsRepository.save(freecom_accreditation_ieps);
        return result;
    }

    /**
     *  Get all the freecom_accreditation_ieps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_accreditation_ieps> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_accreditation_ieps");
        Page<Freecom_accreditation_ieps> result = freecom_accreditation_iepsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_accreditation_ieps by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_accreditation_ieps findOne(Long id) {
        log.debug("Request to get Freecom_accreditation_ieps : {}", id);
        Freecom_accreditation_ieps freecom_accreditation_ieps = freecom_accreditation_iepsRepository.findOne(id);
        return freecom_accreditation_ieps;
    }

    /**
     *  Delete the  freecom_accreditation_ieps by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_accreditation_ieps : {}", id);
        freecom_accreditation_iepsRepository.delete(id);
    }
}
