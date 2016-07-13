package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_doneesService;
import org.megapractical.billingplatform.domain.Freecom_donees;
import org.megapractical.billingplatform.repository.Freecom_doneesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_donees.
 */
@Service
@Transactional
public class Freecom_doneesServiceImpl implements Freecom_doneesService{

    private final Logger log = LoggerFactory.getLogger(Freecom_doneesServiceImpl.class);
    
    @Inject
    private Freecom_doneesRepository freecom_doneesRepository;
    
    /**
     * Save a freecom_donees.
     * 
     * @param freecom_donees the entity to save
     * @return the persisted entity
     */
    public Freecom_donees save(Freecom_donees freecom_donees) {
        log.debug("Request to save Freecom_donees : {}", freecom_donees);
        Freecom_donees result = freecom_doneesRepository.save(freecom_donees);
        return result;
    }

    /**
     *  Get all the freecom_donees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_donees> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_donees");
        Page<Freecom_donees> result = freecom_doneesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_donees by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_donees findOne(Long id) {
        log.debug("Request to get Freecom_donees : {}", id);
        Freecom_donees freecom_donees = freecom_doneesRepository.findOne(id);
        return freecom_donees;
    }

    /**
     *  Delete the  freecom_donees by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_donees : {}", id);
        freecom_doneesRepository.delete(id);
    }
}
