package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.ComplementService;
import org.megapractical.billingplatform.domain.Complement;
import org.megapractical.billingplatform.repository.ComplementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Complement.
 */
@Service
@Transactional
public class ComplementServiceImpl implements ComplementService{

    private final Logger log = LoggerFactory.getLogger(ComplementServiceImpl.class);
    
    @Inject
    private ComplementRepository complementRepository;
    
    /**
     * Save a complement.
     * 
     * @param complement the entity to save
     * @return the persisted entity
     */
    public Complement save(Complement complement) {
        log.debug("Request to save Complement : {}", complement);
        Complement result = complementRepository.save(complement);
        return result;
    }

    /**
     *  Get all the complements.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Complement> findAll(Pageable pageable) {
        log.debug("Request to get all Complements");
        Page<Complement> result = complementRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one complement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Complement findOne(Long id) {
        log.debug("Request to get Complement : {}", id);
        Complement complement = complementRepository.findOne(id);
        return complement;
    }

    /**
     *  Delete the  complement by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Complement : {}", id);
        complementRepository.delete(id);
    }
}
