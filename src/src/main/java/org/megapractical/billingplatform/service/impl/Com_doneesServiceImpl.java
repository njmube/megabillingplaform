package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_doneesService;
import org.megapractical.billingplatform.domain.Com_donees;
import org.megapractical.billingplatform.repository.Com_doneesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_donees.
 */
@Service
@Transactional
public class Com_doneesServiceImpl implements Com_doneesService{

    private final Logger log = LoggerFactory.getLogger(Com_doneesServiceImpl.class);
    
    @Inject
    private Com_doneesRepository com_doneesRepository;
    
    /**
     * Save a com_donees.
     * 
     * @param com_donees the entity to save
     * @return the persisted entity
     */
    public Com_donees save(Com_donees com_donees) {
        log.debug("Request to save Com_donees : {}", com_donees);
        Com_donees result = com_doneesRepository.save(com_donees);
        return result;
    }

    /**
     *  Get all the com_donees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_donees> findAll(Pageable pageable) {
        log.debug("Request to get all Com_donees");
        Page<Com_donees> result = com_doneesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_donees by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_donees findOne(Long id) {
        log.debug("Request to get Com_donees : {}", id);
        Com_donees com_donees = com_doneesRepository.findOne(id);
        return com_donees;
    }

    /**
     *  Delete the  com_donees by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_donees : {}", id);
        com_doneesRepository.delete(id);
    }
}
