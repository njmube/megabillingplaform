package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.repository.C_state_eventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_state_event.
 */
@Service
@Transactional
public class C_state_eventServiceImpl implements C_state_eventService{

    private final Logger log = LoggerFactory.getLogger(C_state_eventServiceImpl.class);
    
    @Inject
    private C_state_eventRepository c_state_eventRepository;
    
    /**
     * Save a c_state_event.
     * 
     * @param c_state_event the entity to save
     * @return the persisted entity
     */
    public C_state_event save(C_state_event c_state_event) {
        log.debug("Request to save C_state_event : {}", c_state_event);
        C_state_event result = c_state_eventRepository.save(c_state_event);
        return result;
    }

    /**
     *  Get all the c_state_events.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_state_event> findAll(Pageable pageable) {
        log.debug("Request to get all C_state_events");
        Page<C_state_event> result = c_state_eventRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_state_event by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_state_event findOne(Long id) {
        log.debug("Request to get C_state_event : {}", id);
        C_state_event c_state_event = c_state_eventRepository.findOne(id);
        return c_state_event;
    }

    /**
     *  Delete the  c_state_event by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_state_event : {}", id);
        c_state_eventRepository.delete(id);
    }
}
