package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_pficService;
import org.megapractical.billingplatform.domain.Com_pfic;
import org.megapractical.billingplatform.repository.Com_pficRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_pfic.
 */
@Service
@Transactional
public class Com_pficServiceImpl implements Com_pficService{

    private final Logger log = LoggerFactory.getLogger(Com_pficServiceImpl.class);
    
    @Inject
    private Com_pficRepository com_pficRepository;
    
    /**
     * Save a com_pfic.
     * 
     * @param com_pfic the entity to save
     * @return the persisted entity
     */
    public Com_pfic save(Com_pfic com_pfic) {
        log.debug("Request to save Com_pfic : {}", com_pfic);
        Com_pfic result = com_pficRepository.save(com_pfic);
        return result;
    }

    /**
     *  Get all the com_pfics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_pfic> findAll(Pageable pageable) {
        log.debug("Request to get all Com_pfics");
        Page<Com_pfic> result = com_pficRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_pfic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_pfic findOne(Long id) {
        log.debug("Request to get Com_pfic : {}", id);
        Com_pfic com_pfic = com_pficRepository.findOne(id);
        return com_pfic;
    }

    /**
     *  Delete the  com_pfic by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_pfic : {}", id);
        com_pficRepository.delete(id);
    }
}
