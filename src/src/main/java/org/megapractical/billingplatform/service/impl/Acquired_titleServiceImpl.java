package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Acquired_titleService;
import org.megapractical.billingplatform.domain.Acquired_title;
import org.megapractical.billingplatform.repository.Acquired_titleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Acquired_title.
 */
@Service
@Transactional
public class Acquired_titleServiceImpl implements Acquired_titleService{

    private final Logger log = LoggerFactory.getLogger(Acquired_titleServiceImpl.class);
    
    @Inject
    private Acquired_titleRepository acquired_titleRepository;
    
    /**
     * Save a acquired_title.
     * 
     * @param acquired_title the entity to save
     * @return the persisted entity
     */
    public Acquired_title save(Acquired_title acquired_title) {
        log.debug("Request to save Acquired_title : {}", acquired_title);
        Acquired_title result = acquired_titleRepository.save(acquired_title);
        return result;
    }

    /**
     *  Get all the acquired_titles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Acquired_title> findAll(Pageable pageable) {
        log.debug("Request to get all Acquired_titles");
        Page<Acquired_title> result = acquired_titleRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one acquired_title by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Acquired_title findOne(Long id) {
        log.debug("Request to get Acquired_title : {}", id);
        Acquired_title acquired_title = acquired_titleRepository.findOne(id);
        return acquired_title;
    }

    /**
     *  Delete the  acquired_title by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Acquired_title : {}", id);
        acquired_titleRepository.delete(id);
    }
}
