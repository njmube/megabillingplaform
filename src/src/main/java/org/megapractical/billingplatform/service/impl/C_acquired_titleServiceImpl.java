package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_acquired_titleService;
import org.megapractical.billingplatform.domain.C_acquired_title;
import org.megapractical.billingplatform.repository.C_acquired_titleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_acquired_title.
 */
@Service
@Transactional
public class C_acquired_titleServiceImpl implements C_acquired_titleService{

    private final Logger log = LoggerFactory.getLogger(C_acquired_titleServiceImpl.class);
    
    @Inject
    private C_acquired_titleRepository c_acquired_titleRepository;
    
    /**
     * Save a c_acquired_title.
     * 
     * @param c_acquired_title the entity to save
     * @return the persisted entity
     */
    public C_acquired_title save(C_acquired_title c_acquired_title) {
        log.debug("Request to save C_acquired_title : {}", c_acquired_title);
        C_acquired_title result = c_acquired_titleRepository.save(c_acquired_title);
        return result;
    }

    /**
     *  Get all the c_acquired_titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_acquired_title> findAll(Pageable pageable) {
        log.debug("Request to get all C_acquired_titles");
        Page<C_acquired_title> result = c_acquired_titleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_acquired_title by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_acquired_title findOne(Long id) {
        log.debug("Request to get C_acquired_title : {}", id);
        C_acquired_title c_acquired_title = c_acquired_titleRepository.findOne(id);
        return c_acquired_title;
    }

    /**
     *  Delete the  c_acquired_title by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_acquired_title : {}", id);
        c_acquired_titleRepository.delete(id);
    }
}
