package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_transit_typeService;
import org.megapractical.billingplatform.domain.C_transit_type;
import org.megapractical.billingplatform.repository.C_transit_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_transit_type.
 */
@Service
@Transactional
public class C_transit_typeServiceImpl implements C_transit_typeService{

    private final Logger log = LoggerFactory.getLogger(C_transit_typeServiceImpl.class);
    
    @Inject
    private C_transit_typeRepository c_transit_typeRepository;
    
    /**
     * Save a c_transit_type.
     * 
     * @param c_transit_type the entity to save
     * @return the persisted entity
     */
    public C_transit_type save(C_transit_type c_transit_type) {
        log.debug("Request to save C_transit_type : {}", c_transit_type);
        C_transit_type result = c_transit_typeRepository.save(c_transit_type);
        return result;
    }

    /**
     *  Get all the c_transit_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_transit_type> findAll(Pageable pageable) {
        log.debug("Request to get all C_transit_types");
        Page<C_transit_type> result = c_transit_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_transit_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_transit_type findOne(Long id) {
        log.debug("Request to get C_transit_type : {}", id);
        C_transit_type c_transit_type = c_transit_typeRepository.findOne(id);
        return c_transit_type;
    }

    /**
     *  Delete the  c_transit_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_transit_type : {}", id);
        c_transit_typeRepository.delete(id);
    }
}
