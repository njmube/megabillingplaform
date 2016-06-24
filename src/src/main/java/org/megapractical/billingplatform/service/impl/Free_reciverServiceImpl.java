package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Free_reciverService;
import org.megapractical.billingplatform.domain.Free_reciver;
import org.megapractical.billingplatform.repository.Free_reciverRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Free_reciver.
 */
@Service
@Transactional
public class Free_reciverServiceImpl implements Free_reciverService{

    private final Logger log = LoggerFactory.getLogger(Free_reciverServiceImpl.class);
    
    @Inject
    private Free_reciverRepository free_reciverRepository;
    
    /**
     * Save a free_reciver.
     * 
     * @param free_reciver the entity to save
     * @return the persisted entity
     */
    public Free_reciver save(Free_reciver free_reciver) {
        log.debug("Request to save Free_reciver : {}", free_reciver);
        Free_reciver result = free_reciverRepository.save(free_reciver);
        return result;
    }

    /**
     *  Get all the free_recivers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Free_reciver> findAll(Pageable pageable) {
        log.debug("Request to get all Free_recivers");
        Page<Free_reciver> result = free_reciverRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one free_reciver by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Free_reciver findOne(Long id) {
        log.debug("Request to get Free_reciver : {}", id);
        Free_reciver free_reciver = free_reciverRepository.findOne(id);
        return free_reciver;
    }

    /**
     *  Delete the  free_reciver by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Free_reciver : {}", id);
        free_reciverRepository.delete(id);
    }
}
