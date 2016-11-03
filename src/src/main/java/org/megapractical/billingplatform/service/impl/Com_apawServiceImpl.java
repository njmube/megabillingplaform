package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_apawService;
import org.megapractical.billingplatform.domain.Com_apaw;
import org.megapractical.billingplatform.repository.Com_apawRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_apaw.
 */
@Service
@Transactional
public class Com_apawServiceImpl implements Com_apawService{

    private final Logger log = LoggerFactory.getLogger(Com_apawServiceImpl.class);
    
    @Inject
    private Com_apawRepository com_apawRepository;
    
    /**
     * Save a com_apaw.
     * 
     * @param com_apaw the entity to save
     * @return the persisted entity
     */
    public Com_apaw save(Com_apaw com_apaw) {
        log.debug("Request to save Com_apaw : {}", com_apaw);
        Com_apaw result = com_apawRepository.save(com_apaw);
        return result;
    }

    /**
     *  Get all the com_apaws.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_apaw> findAll(Pageable pageable) {
        log.debug("Request to get all Com_apaws");
        Page<Com_apaw> result = com_apawRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_apaw by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_apaw findOne(Long id) {
        log.debug("Request to get Com_apaw : {}", id);
        Com_apaw com_apaw = com_apawRepository.findOne(id);
        return com_apaw;
    }

    /**
     *  Delete the  com_apaw by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_apaw : {}", id);
        com_apawRepository.delete(id);
    }
}
