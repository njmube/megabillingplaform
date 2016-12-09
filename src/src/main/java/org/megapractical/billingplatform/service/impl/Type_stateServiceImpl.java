package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Type_stateService;
import org.megapractical.billingplatform.domain.Type_state;
import org.megapractical.billingplatform.repository.Type_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Type_state.
 */
@Service
@Transactional
public class Type_stateServiceImpl implements Type_stateService{

    private final Logger log = LoggerFactory.getLogger(Type_stateServiceImpl.class);
    
    @Inject
    private Type_stateRepository type_stateRepository;
    
    /**
     * Save a type_state.
     * 
     * @param type_state the entity to save
     * @return the persisted entity
     */
    public Type_state save(Type_state type_state) {
        log.debug("Request to save Type_state : {}", type_state);
        Type_state result = type_stateRepository.save(type_state);
        return result;
    }

    /**
     *  Get all the type_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Type_state> findAll(Pageable pageable) {
        log.debug("Request to get all Type_states");
        Page<Type_state> result = type_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one type_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Type_state findOne(Long id) {
        log.debug("Request to get Type_state : {}", id);
        Type_state type_state = type_stateRepository.findOne(id);
        return type_state;
    }

    /**
     *  Delete the  type_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Type_state : {}", id);
        type_stateRepository.delete(id);
    }
}
