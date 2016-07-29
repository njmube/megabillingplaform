package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_type_roadService;
import org.megapractical.billingplatform.domain.C_type_road;
import org.megapractical.billingplatform.repository.C_type_roadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_type_road.
 */
@Service
@Transactional
public class C_type_roadServiceImpl implements C_type_roadService{

    private final Logger log = LoggerFactory.getLogger(C_type_roadServiceImpl.class);
    
    @Inject
    private C_type_roadRepository c_type_roadRepository;
    
    /**
     * Save a c_type_road.
     * 
     * @param c_type_road the entity to save
     * @return the persisted entity
     */
    public C_type_road save(C_type_road c_type_road) {
        log.debug("Request to save C_type_road : {}", c_type_road);
        C_type_road result = c_type_roadRepository.save(c_type_road);
        return result;
    }

    /**
     *  Get all the c_type_roads.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_type_road> findAll(Pageable pageable) {
        log.debug("Request to get all C_type_roads");
        Page<C_type_road> result = c_type_roadRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_type_road by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_type_road findOne(Long id) {
        log.debug("Request to get C_type_road : {}", id);
        C_type_road c_type_road = c_type_roadRepository.findOne(id);
        return c_type_road;
    }

    /**
     *  Delete the  c_type_road by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_type_road : {}", id);
        c_type_roadRepository.delete(id);
    }
}
