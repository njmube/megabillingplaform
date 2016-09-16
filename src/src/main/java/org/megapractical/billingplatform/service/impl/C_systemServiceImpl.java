package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_systemService;
import org.megapractical.billingplatform.domain.C_system;
import org.megapractical.billingplatform.repository.C_systemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_system.
 */
@Service
@Transactional
public class C_systemServiceImpl implements C_systemService{

    private final Logger log = LoggerFactory.getLogger(C_systemServiceImpl.class);
    
    @Inject
    private C_systemRepository c_systemRepository;
    
    /**
     * Save a c_system.
     * 
     * @param c_system the entity to save
     * @return the persisted entity
     */
    public C_system save(C_system c_system) {
        log.debug("Request to save C_system : {}", c_system);
        C_system result = c_systemRepository.save(c_system);
        return result;
    }

    /**
     *  Get all the c_systems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_system> findAll(Pageable pageable) {
        log.debug("Request to get all C_systems");
        Page<C_system> result = c_systemRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_system by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_system findOne(Long id) {
        log.debug("Request to get C_system : {}", id);
        C_system c_system = c_systemRepository.findOne(id);
        return c_system;
    }

    /**
     *  Delete the  c_system by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_system : {}", id);
        c_systemRepository.delete(id);
    }
}
