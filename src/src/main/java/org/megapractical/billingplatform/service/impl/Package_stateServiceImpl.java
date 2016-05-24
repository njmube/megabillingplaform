package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Package_stateService;
import org.megapractical.billingplatform.domain.Package_state;
import org.megapractical.billingplatform.repository.Package_stateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Package_state.
 */
@Service
@Transactional
public class Package_stateServiceImpl implements Package_stateService{

    private final Logger log = LoggerFactory.getLogger(Package_stateServiceImpl.class);
    
    @Inject
    private Package_stateRepository package_stateRepository;
    
    /**
     * Save a package_state.
     * 
     * @param package_state the entity to save
     * @return the persisted entity
     */
    public Package_state save(Package_state package_state) {
        log.debug("Request to save Package_state : {}", package_state);
        Package_state result = package_stateRepository.save(package_state);
        return result;
    }

    /**
     *  Get all the package_states.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Package_state> findAll(Pageable pageable) {
        log.debug("Request to get all Package_states");
        Page<Package_state> result = package_stateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one package_state by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Package_state findOne(Long id) {
        log.debug("Request to get Package_state : {}", id);
        Package_state package_state = package_stateRepository.findOne(id);
        return package_state;
    }

    /**
     *  Delete the  package_state by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Package_state : {}", id);
        package_stateRepository.delete(id);
    }
}
