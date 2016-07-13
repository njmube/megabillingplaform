package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.School_levelService;
import org.megapractical.billingplatform.domain.School_level;
import org.megapractical.billingplatform.repository.School_levelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing School_level.
 */
@Service
@Transactional
public class School_levelServiceImpl implements School_levelService{

    private final Logger log = LoggerFactory.getLogger(School_levelServiceImpl.class);
    
    @Inject
    private School_levelRepository school_levelRepository;
    
    /**
     * Save a school_level.
     * 
     * @param school_level the entity to save
     * @return the persisted entity
     */
    public School_level save(School_level school_level) {
        log.debug("Request to save School_level : {}", school_level);
        School_level result = school_levelRepository.save(school_level);
        return result;
    }

    /**
     *  Get all the school_levels.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<School_level> findAll(Pageable pageable) {
        log.debug("Request to get all School_levels");
        Page<School_level> result = school_levelRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one school_level by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public School_level findOne(Long id) {
        log.debug("Request to get School_level : {}", id);
        School_level school_level = school_levelRepository.findOne(id);
        return school_level;
    }

    /**
     *  Delete the  school_level by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete School_level : {}", id);
        school_levelRepository.delete(id);
    }
}
