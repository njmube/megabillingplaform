package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_key_pedimentService;
import org.megapractical.billingplatform.domain.C_key_pediment;
import org.megapractical.billingplatform.repository.C_key_pedimentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_key_pediment.
 */
@Service
@Transactional
public class C_key_pedimentServiceImpl implements C_key_pedimentService{

    private final Logger log = LoggerFactory.getLogger(C_key_pedimentServiceImpl.class);
    
    @Inject
    private C_key_pedimentRepository c_key_pedimentRepository;
    
    /**
     * Save a c_key_pediment.
     * 
     * @param c_key_pediment the entity to save
     * @return the persisted entity
     */
    public C_key_pediment save(C_key_pediment c_key_pediment) {
        log.debug("Request to save C_key_pediment : {}", c_key_pediment);
        C_key_pediment result = c_key_pedimentRepository.save(c_key_pediment);
        return result;
    }

    /**
     *  Get all the c_key_pediments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_key_pediment> findAll(Pageable pageable) {
        log.debug("Request to get all C_key_pediments");
        Page<C_key_pediment> result = c_key_pedimentRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_key_pediment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_key_pediment findOne(Long id) {
        log.debug("Request to get C_key_pediment : {}", id);
        C_key_pediment c_key_pediment = c_key_pedimentRepository.findOne(id);
        return c_key_pediment;
    }

    /**
     *  Delete the  c_key_pediment by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_key_pediment : {}", id);
        c_key_pedimentRepository.delete(id);
    }
}
