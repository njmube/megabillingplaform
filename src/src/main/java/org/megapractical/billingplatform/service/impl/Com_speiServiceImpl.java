package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_speiService;
import org.megapractical.billingplatform.domain.Com_spei;
import org.megapractical.billingplatform.repository.Com_speiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_spei.
 */
@Service
@Transactional
public class Com_speiServiceImpl implements Com_speiService{

    private final Logger log = LoggerFactory.getLogger(Com_speiServiceImpl.class);
    
    @Inject
    private Com_speiRepository com_speiRepository;
    
    /**
     * Save a com_spei.
     * 
     * @param com_spei the entity to save
     * @return the persisted entity
     */
    public Com_spei save(Com_spei com_spei) {
        log.debug("Request to save Com_spei : {}", com_spei);
        Com_spei result = com_speiRepository.save(com_spei);
        return result;
    }

    /**
     *  Get all the com_speis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_spei> findAll(Pageable pageable) {
        log.debug("Request to get all Com_speis");
        Page<Com_spei> result = com_speiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_spei by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_spei findOne(Long id) {
        log.debug("Request to get Com_spei : {}", id);
        Com_spei com_spei = com_speiRepository.findOne(id);
        return com_spei;
    }

    /**
     *  Delete the  com_spei by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_spei : {}", id);
        com_speiRepository.delete(id);
    }
}
