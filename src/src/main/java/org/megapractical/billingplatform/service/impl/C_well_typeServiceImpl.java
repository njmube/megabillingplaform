package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_well_typeService;
import org.megapractical.billingplatform.domain.C_well_type;
import org.megapractical.billingplatform.repository.C_well_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_well_type.
 */
@Service
@Transactional
public class C_well_typeServiceImpl implements C_well_typeService{

    private final Logger log = LoggerFactory.getLogger(C_well_typeServiceImpl.class);
    
    @Inject
    private C_well_typeRepository c_well_typeRepository;
    
    /**
     * Save a c_well_type.
     * 
     * @param c_well_type the entity to save
     * @return the persisted entity
     */
    public C_well_type save(C_well_type c_well_type) {
        log.debug("Request to save C_well_type : {}", c_well_type);
        C_well_type result = c_well_typeRepository.save(c_well_type);
        return result;
    }

    /**
     *  Get all the c_well_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_well_type> findAll(Pageable pageable) {
        log.debug("Request to get all C_well_types");
        Page<C_well_type> result = c_well_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_well_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_well_type findOne(Long id) {
        log.debug("Request to get C_well_type : {}", id);
        C_well_type c_well_type = c_well_typeRepository.findOne(id);
        return c_well_type;
    }

    /**
     *  Delete the  c_well_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_well_type : {}", id);
        c_well_typeRepository.delete(id);
    }
}
