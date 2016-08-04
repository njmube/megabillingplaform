package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_classService;
import org.megapractical.billingplatform.domain.C_class;
import org.megapractical.billingplatform.repository.C_classRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_class.
 */
@Service
@Transactional
public class C_classServiceImpl implements C_classService{

    private final Logger log = LoggerFactory.getLogger(C_classServiceImpl.class);
    
    @Inject
    private C_classRepository c_classRepository;
    
    /**
     * Save a c_class.
     * 
     * @param c_class the entity to save
     * @return the persisted entity
     */
    public C_class save(C_class c_class) {
        log.debug("Request to save C_class : {}", c_class);
        C_class result = c_classRepository.save(c_class);
        return result;
    }

    /**
     *  Get all the c_classes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_class> findAll(Pageable pageable) {
        log.debug("Request to get all C_classes");
        Page<C_class> result = c_classRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_class by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_class findOne(Long id) {
        log.debug("Request to get C_class : {}", id);
        C_class c_class = c_classRepository.findOne(id);
        return c_class;
    }

    /**
     *  Delete the  c_class by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_class : {}", id);
        c_classRepository.delete(id);
    }
}
