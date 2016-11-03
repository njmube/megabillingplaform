package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_addresseeService;
import org.megapractical.billingplatform.domain.Com_addressee;
import org.megapractical.billingplatform.repository.Com_addresseeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_addressee.
 */
@Service
@Transactional
public class Com_addresseeServiceImpl implements Com_addresseeService{

    private final Logger log = LoggerFactory.getLogger(Com_addresseeServiceImpl.class);
    
    @Inject
    private Com_addresseeRepository com_addresseeRepository;
    
    /**
     * Save a com_addressee.
     * 
     * @param com_addressee the entity to save
     * @return the persisted entity
     */
    public Com_addressee save(Com_addressee com_addressee) {
        log.debug("Request to save Com_addressee : {}", com_addressee);
        Com_addressee result = com_addresseeRepository.save(com_addressee);
        return result;
    }

    /**
     *  Get all the com_addressees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_addressee> findAll(Pageable pageable) {
        log.debug("Request to get all Com_addressees");
        Page<Com_addressee> result = com_addresseeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_addressee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_addressee findOne(Long id) {
        log.debug("Request to get Com_addressee : {}", id);
        Com_addressee com_addressee = com_addresseeRepository.findOne(id);
        return com_addressee;
    }

    /**
     *  Delete the  com_addressee by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_addressee : {}", id);
        com_addresseeRepository.delete(id);
    }
}
