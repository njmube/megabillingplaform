package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.File_stateService;
import org.megapractical.billingplatform.domain.File_state;
import org.megapractical.billingplatform.repository.File_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing File_state.
 */
@Service
@Transactional
public class File_stateServiceImpl implements File_stateService{

    private final Logger log = LoggerFactory.getLogger(File_stateServiceImpl.class);
    
    @Inject
    private File_stateRepository file_stateRepository;
    
    /**
     * Save a file_state.
     * 
     * @param file_state the entity to save
     * @return the persisted entity
     */
    public File_state save(File_state file_state) {
        log.debug("Request to save File_state : {}", file_state);
        File_state result = file_stateRepository.save(file_state);
        return result;
    }

    /**
     *  Get all the file_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<File_state> findAll(Pageable pageable) {
        log.debug("Request to get all File_states");
        Page<File_state> result = file_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one file_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public File_state findOne(Long id) {
        log.debug("Request to get File_state : {}", id);
        File_state file_state = file_stateRepository.findOne(id);
        return file_state;
    }

    /**
     *  Delete the  file_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete File_state : {}", id);
        file_stateRepository.delete(id);
    }
}
