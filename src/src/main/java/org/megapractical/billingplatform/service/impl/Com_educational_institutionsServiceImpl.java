package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_educational_institutionsService;
import org.megapractical.billingplatform.domain.Com_educational_institutions;
import org.megapractical.billingplatform.repository.Com_educational_institutionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_educational_institutions.
 */
@Service
@Transactional
public class Com_educational_institutionsServiceImpl implements Com_educational_institutionsService{

    private final Logger log = LoggerFactory.getLogger(Com_educational_institutionsServiceImpl.class);
    
    @Inject
    private Com_educational_institutionsRepository com_educational_institutionsRepository;
    
    /**
     * Save a com_educational_institutions.
     * 
     * @param com_educational_institutions the entity to save
     * @return the persisted entity
     */
    public Com_educational_institutions save(Com_educational_institutions com_educational_institutions) {
        log.debug("Request to save Com_educational_institutions : {}", com_educational_institutions);
        Com_educational_institutions result = com_educational_institutionsRepository.save(com_educational_institutions);
        return result;
    }

    /**
     *  Get all the com_educational_institutions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_educational_institutions> findAll(Pageable pageable) {
        log.debug("Request to get all Com_educational_institutions");
        Page<Com_educational_institutions> result = com_educational_institutionsRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_educational_institutions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_educational_institutions findOne(Long id) {
        log.debug("Request to get Com_educational_institutions : {}", id);
        Com_educational_institutions com_educational_institutions = com_educational_institutionsRepository.findOne(id);
        return com_educational_institutions;
    }

    /**
     *  Delete the  com_educational_institutions by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_educational_institutions : {}", id);
        com_educational_institutionsRepository.delete(id);
    }
}
