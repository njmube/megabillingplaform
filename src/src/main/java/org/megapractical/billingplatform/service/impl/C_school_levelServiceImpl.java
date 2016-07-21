package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_school_levelService;
import org.megapractical.billingplatform.domain.C_school_level;
import org.megapractical.billingplatform.repository.C_school_levelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_school_level.
 */
@Service
@Transactional
public class C_school_levelServiceImpl implements C_school_levelService{

    private final Logger log = LoggerFactory.getLogger(C_school_levelServiceImpl.class);
    
    @Inject
    private C_school_levelRepository c_school_levelRepository;
    
    /**
     * Save a c_school_level.
     * 
     * @param c_school_level the entity to save
     * @return the persisted entity
     */
    public C_school_level save(C_school_level c_school_level) {
        log.debug("Request to save C_school_level : {}", c_school_level);
        C_school_level result = c_school_levelRepository.save(c_school_level);
        return result;
    }

    /**
     *  Get all the c_school_levels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_school_level> findAll(Pageable pageable) {
        log.debug("Request to get all C_school_levels");
        Page<C_school_level> result = c_school_levelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_school_level by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_school_level findOne(Long id) {
        log.debug("Request to get C_school_level : {}", id);
        C_school_level c_school_level = c_school_levelRepository.findOne(id);
        return c_school_level;
    }

    /**
     *  Delete the  c_school_level by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_school_level : {}", id);
        c_school_levelRepository.delete(id);
    }
}
