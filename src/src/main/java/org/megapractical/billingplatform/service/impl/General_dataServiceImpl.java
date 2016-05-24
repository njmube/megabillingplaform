package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.General_dataService;
import org.megapractical.billingplatform.domain.General_data;
import org.megapractical.billingplatform.repository.General_dataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing General_data.
 */
@Service
@Transactional
public class General_dataServiceImpl implements General_dataService{

    private final Logger log = LoggerFactory.getLogger(General_dataServiceImpl.class);
    
    @Inject
    private General_dataRepository general_dataRepository;
    
    /**
     * Save a general_data.
     * 
     * @param general_data the entity to save
     * @return the persisted entity
     */
    public General_data save(General_data general_data) {
        log.debug("Request to save General_data : {}", general_data);
        General_data result = general_dataRepository.save(general_data);
        return result;
    }

    /**
     *  Get all the general_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<General_data> findAll(Pageable pageable) {
        log.debug("Request to get all General_data");
        Page<General_data> result = general_dataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one general_data by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public General_data findOne(Long id) {
        log.debug("Request to get General_data : {}", id);
        General_data general_data = general_dataRepository.findOne(id);
        return general_data;
    }

    /**
     *  Delete the  general_data by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete General_data : {}", id);
        general_dataRepository.delete(id);
    }
}
