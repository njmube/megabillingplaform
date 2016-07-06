package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Type_taxpayerService;
import org.megapractical.billingplatform.domain.Type_taxpayer;
import org.megapractical.billingplatform.repository.Type_taxpayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Type_taxpayer.
 */
@Service
@Transactional
public class Type_taxpayerServiceImpl implements Type_taxpayerService{

    private final Logger log = LoggerFactory.getLogger(Type_taxpayerServiceImpl.class);
    
    @Inject
    private Type_taxpayerRepository type_taxpayerRepository;
    
    /**
     * Save a type_taxpayer.
     * 
     * @param type_taxpayer the entity to save
     * @return the persisted entity
     */
    public Type_taxpayer save(Type_taxpayer type_taxpayer) {
        log.debug("Request to save Type_taxpayer : {}", type_taxpayer);
        Type_taxpayer result = type_taxpayerRepository.save(type_taxpayer);
        return result;
    }

    /**
     *  Get all the type_taxpayers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Type_taxpayer> findAll(Pageable pageable) {
        log.debug("Request to get all Type_taxpayers");
        Page<Type_taxpayer> result = type_taxpayerRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one type_taxpayer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Type_taxpayer findOne(Long id) {
        log.debug("Request to get Type_taxpayer : {}", id);
        Type_taxpayer type_taxpayer = type_taxpayerRepository.findOne(id);
        return type_taxpayer;
    }

    /**
     *  Delete the  type_taxpayer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Type_taxpayer : {}", id);
        type_taxpayerRepository.delete(id);
    }
}
