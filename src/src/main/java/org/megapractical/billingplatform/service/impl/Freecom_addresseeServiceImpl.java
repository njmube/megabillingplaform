package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_addresseeService;
import org.megapractical.billingplatform.domain.Freecom_addressee;
import org.megapractical.billingplatform.repository.Freecom_addresseeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_addressee.
 */
@Service
@Transactional
public class Freecom_addresseeServiceImpl implements Freecom_addresseeService{

    private final Logger log = LoggerFactory.getLogger(Freecom_addresseeServiceImpl.class);
    
    @Inject
    private Freecom_addresseeRepository freecom_addresseeRepository;
    
    /**
     * Save a freecom_addressee.
     * 
     * @param freecom_addressee the entity to save
     * @return the persisted entity
     */
    public Freecom_addressee save(Freecom_addressee freecom_addressee) {
        log.debug("Request to save Freecom_addressee : {}", freecom_addressee);
        Freecom_addressee result = freecom_addresseeRepository.save(freecom_addressee);
        return result;
    }

    /**
     *  Get all the freecom_addressees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_addressee> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_addressees");
        Page<Freecom_addressee> result = freecom_addresseeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_addressee by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_addressee findOne(Long id) {
        log.debug("Request to get Freecom_addressee : {}", id);
        Freecom_addressee freecom_addressee = freecom_addresseeRepository.findOne(id);
        return freecom_addressee;
    }

    /**
     *  Delete the  freecom_addressee by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_addressee : {}", id);
        freecom_addresseeRepository.delete(id);
    }
}
