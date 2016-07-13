package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.CommitteeService;
import org.megapractical.billingplatform.domain.Committee;
import org.megapractical.billingplatform.repository.CommitteeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Committee.
 */
@Service
@Transactional
public class CommitteeServiceImpl implements CommitteeService{

    private final Logger log = LoggerFactory.getLogger(CommitteeServiceImpl.class);
    
    @Inject
    private CommitteeRepository committeeRepository;
    
    /**
     * Save a committee.
     * 
     * @param committee the entity to save
     * @return the persisted entity
     */
    public Committee save(Committee committee) {
        log.debug("Request to save Committee : {}", committee);
        Committee result = committeeRepository.save(committee);
        return result;
    }

    /**
     *  Get all the committees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Committee> findAll(Pageable pageable) {
        log.debug("Request to get all Committees");
        Page<Committee> result = committeeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one committee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Committee findOne(Long id) {
        log.debug("Request to get Committee : {}", id);
        Committee committee = committeeRepository.findOne(id);
        return committee;
    }

    /**
     *  Delete the  committee by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Committee : {}", id);
        committeeRepository.delete(id);
    }
}
