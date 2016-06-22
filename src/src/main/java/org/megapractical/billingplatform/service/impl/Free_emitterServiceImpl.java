package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_emitterService;
import org.megapractical.billingplatform.domain.Free_emitter;
import org.megapractical.billingplatform.repository.Free_emitterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_emitter.
 */
@Service
@Transactional
public class Free_emitterServiceImpl implements Free_emitterService{

    private final Logger log = LoggerFactory.getLogger(Free_emitterServiceImpl.class);
    
    @Inject
    private Free_emitterRepository free_emitterRepository;
    
    /**
     * Save a free_emitter.
     * 
     * @param free_emitter the entity to save
     * @return the persisted entity
     */
    public Free_emitter save(Free_emitter free_emitter) {
        log.debug("Request to save Free_emitter : {}", free_emitter);
        Free_emitter result = free_emitterRepository.save(free_emitter);
        return result;
    }

    /**
     *  Get all the free_emitters.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_emitter> findAll(Pageable pageable) {
        log.debug("Request to get all Free_emitters");
        Page<Free_emitter> result = free_emitterRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_emitter by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_emitter findOne(Long id) {
        log.debug("Request to get Free_emitter : {}", id);
        Free_emitter free_emitter = free_emitterRepository.findOne(id);
        return free_emitter;
    }

    /**
     *  Delete the  free_emitter by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_emitter : {}", id);
        free_emitterRepository.delete(id);
    }
}
