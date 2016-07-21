package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_scope_typeService;
import org.megapractical.billingplatform.domain.C_scope_type;
import org.megapractical.billingplatform.repository.C_scope_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_scope_type.
 */
@Service
@Transactional
public class C_scope_typeServiceImpl implements C_scope_typeService{

    private final Logger log = LoggerFactory.getLogger(C_scope_typeServiceImpl.class);
    
    @Inject
    private C_scope_typeRepository c_scope_typeRepository;
    
    /**
     * Save a c_scope_type.
     * 
     * @param c_scope_type the entity to save
     * @return the persisted entity
     */
    public C_scope_type save(C_scope_type c_scope_type) {
        log.debug("Request to save C_scope_type : {}", c_scope_type);
        C_scope_type result = c_scope_typeRepository.save(c_scope_type);
        return result;
    }

    /**
     *  Get all the c_scope_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_scope_type> findAll(Pageable pageable) {
        log.debug("Request to get all C_scope_types");
        Page<C_scope_type> result = c_scope_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_scope_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_scope_type findOne(Long id) {
        log.debug("Request to get C_scope_type : {}", id);
        C_scope_type c_scope_type = c_scope_typeRepository.findOne(id);
        return c_scope_type;
    }

    /**
     *  Delete the  c_scope_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_scope_type : {}", id);
        c_scope_typeRepository.delete(id);
    }
}
