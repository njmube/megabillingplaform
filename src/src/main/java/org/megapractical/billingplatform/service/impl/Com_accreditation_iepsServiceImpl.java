package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_accreditation_iepsService;
import org.megapractical.billingplatform.domain.Com_accreditation_ieps;
import org.megapractical.billingplatform.repository.Com_accreditation_iepsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_accreditation_ieps.
 */
@Service
@Transactional
public class Com_accreditation_iepsServiceImpl implements Com_accreditation_iepsService{

    private final Logger log = LoggerFactory.getLogger(Com_accreditation_iepsServiceImpl.class);
    
    @Inject
    private Com_accreditation_iepsRepository com_accreditation_iepsRepository;
    
    /**
     * Save a com_accreditation_ieps.
     * 
     * @param com_accreditation_ieps the entity to save
     * @return the persisted entity
     */
    public Com_accreditation_ieps save(Com_accreditation_ieps com_accreditation_ieps) {
        log.debug("Request to save Com_accreditation_ieps : {}", com_accreditation_ieps);
        Com_accreditation_ieps result = com_accreditation_iepsRepository.save(com_accreditation_ieps);
        return result;
    }

    /**
     *  Get all the com_accreditation_ieps.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_accreditation_ieps> findAll(Pageable pageable) {
        log.debug("Request to get all Com_accreditation_ieps");
        Page<Com_accreditation_ieps> result = com_accreditation_iepsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_accreditation_ieps by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_accreditation_ieps findOne(Long id) {
        log.debug("Request to get Com_accreditation_ieps : {}", id);
        Com_accreditation_ieps com_accreditation_ieps = com_accreditation_iepsRepository.findOne(id);
        return com_accreditation_ieps;
    }

    /**
     *  Delete the  com_accreditation_ieps by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_accreditation_ieps : {}", id);
        com_accreditation_iepsRepository.delete(id);
    }
}
