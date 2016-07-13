package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Process_typeService;
import org.megapractical.billingplatform.domain.Process_type;
import org.megapractical.billingplatform.repository.Process_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Process_type.
 */
@Service
@Transactional
public class Process_typeServiceImpl implements Process_typeService{

    private final Logger log = LoggerFactory.getLogger(Process_typeServiceImpl.class);
    
    @Inject
    private Process_typeRepository process_typeRepository;
    
    /**
     * Save a process_type.
     * 
     * @param process_type the entity to save
     * @return the persisted entity
     */
    public Process_type save(Process_type process_type) {
        log.debug("Request to save Process_type : {}", process_type);
        Process_type result = process_typeRepository.save(process_type);
        return result;
    }

    /**
     *  Get all the process_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Process_type> findAll(Pageable pageable) {
        log.debug("Request to get all Process_types");
        Page<Process_type> result = process_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one process_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Process_type findOne(Long id) {
        log.debug("Request to get Process_type : {}", id);
        Process_type process_type = process_typeRepository.findOne(id);
        return process_type;
    }

    /**
     *  Delete the  process_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Process_type : {}", id);
        process_typeRepository.delete(id);
    }
}
