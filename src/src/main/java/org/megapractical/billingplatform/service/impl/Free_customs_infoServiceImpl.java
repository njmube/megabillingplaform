package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_customs_infoService;
import org.megapractical.billingplatform.domain.Free_customs_info;
import org.megapractical.billingplatform.repository.Free_customs_infoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_customs_info.
 */
@Service
@Transactional
public class Free_customs_infoServiceImpl implements Free_customs_infoService{

    private final Logger log = LoggerFactory.getLogger(Free_customs_infoServiceImpl.class);
    
    @Inject
    private Free_customs_infoRepository free_customs_infoRepository;
    
    /**
     * Save a free_customs_info.
     * 
     * @param free_customs_info the entity to save
     * @return the persisted entity
     */
    public Free_customs_info save(Free_customs_info free_customs_info) {
        log.debug("Request to save Free_customs_info : {}", free_customs_info);
        Free_customs_info result = free_customs_infoRepository.save(free_customs_info);
        return result;
    }

    /**
     *  Get all the free_customs_infos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_customs_info> findAll(Pageable pageable) {
        log.debug("Request to get all Free_customs_infos");
        Page<Free_customs_info> result = free_customs_infoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_customs_info by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_customs_info findOne(Long id) {
        log.debug("Request to get Free_customs_info : {}", id);
        Free_customs_info free_customs_info = free_customs_infoRepository.findOne(id);
        return free_customs_info;
    }

    /**
     *  Delete the  free_customs_info by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_customs_info : {}", id);
        free_customs_infoRepository.delete(id);
    }
}
