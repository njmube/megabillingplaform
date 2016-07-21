package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_process_typeService;
import org.megapractical.billingplatform.domain.C_process_type;
import org.megapractical.billingplatform.repository.C_process_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_process_type.
 */
@Service
@Transactional
public class C_process_typeServiceImpl implements C_process_typeService{

    private final Logger log = LoggerFactory.getLogger(C_process_typeServiceImpl.class);
    
    @Inject
    private C_process_typeRepository c_process_typeRepository;
    
    /**
     * Save a c_process_type.
     * 
     * @param c_process_type the entity to save
     * @return the persisted entity
     */
    public C_process_type save(C_process_type c_process_type) {
        log.debug("Request to save C_process_type : {}", c_process_type);
        C_process_type result = c_process_typeRepository.save(c_process_type);
        return result;
    }

    /**
     *  Get all the c_process_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_process_type> findAll(Pageable pageable) {
        log.debug("Request to get all C_process_types");
        Page<C_process_type> result = c_process_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_process_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_process_type findOne(Long id) {
        log.debug("Request to get C_process_type : {}", id);
        C_process_type c_process_type = c_process_typeRepository.findOne(id);
        return c_process_type;
    }

    /**
     *  Delete the  c_process_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_process_type : {}", id);
        c_process_typeRepository.delete(id);
    }
}
