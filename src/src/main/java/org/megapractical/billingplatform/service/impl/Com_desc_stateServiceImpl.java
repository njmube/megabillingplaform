package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_desc_stateService;
import org.megapractical.billingplatform.domain.Com_desc_state;
import org.megapractical.billingplatform.repository.Com_desc_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_desc_state.
 */
@Service
@Transactional
public class Com_desc_stateServiceImpl implements Com_desc_stateService{

    private final Logger log = LoggerFactory.getLogger(Com_desc_stateServiceImpl.class);
    
    @Inject
    private Com_desc_stateRepository com_desc_stateRepository;
    
    /**
     * Save a com_desc_state.
     * 
     * @param com_desc_state the entity to save
     * @return the persisted entity
     */
    public Com_desc_state save(Com_desc_state com_desc_state) {
        log.debug("Request to save Com_desc_state : {}", com_desc_state);
        Com_desc_state result = com_desc_stateRepository.save(com_desc_state);
        return result;
    }

    /**
     *  Get all the com_desc_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_desc_state> findAll(Pageable pageable) {
        log.debug("Request to get all Com_desc_states");
        Page<Com_desc_state> result = com_desc_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_desc_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_desc_state findOne(Long id) {
        log.debug("Request to get Com_desc_state : {}", id);
        Com_desc_state com_desc_state = com_desc_stateRepository.findOne(id);
        return com_desc_state;
    }

    /**
     *  Delete the  com_desc_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_desc_state : {}", id);
        com_desc_stateRepository.delete(id);
    }
}
