package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_pficService;
import org.megapractical.billingplatform.domain.Freecom_pfic;
import org.megapractical.billingplatform.repository.Freecom_pficRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_pfic.
 */
@Service
@Transactional
public class Freecom_pficServiceImpl implements Freecom_pficService{

    private final Logger log = LoggerFactory.getLogger(Freecom_pficServiceImpl.class);
    
    @Inject
    private Freecom_pficRepository freecom_pficRepository;
    
    /**
     * Save a freecom_pfic.
     * 
     * @param freecom_pfic the entity to save
     * @return the persisted entity
     */
    public Freecom_pfic save(Freecom_pfic freecom_pfic) {
        log.debug("Request to save Freecom_pfic : {}", freecom_pfic);
        Freecom_pfic result = freecom_pficRepository.save(freecom_pfic);
        return result;
    }

    /**
     *  Get all the freecom_pfics.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_pfic> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_pfics");
        Page<Freecom_pfic> result = freecom_pficRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_pfic by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_pfic findOne(Long id) {
        log.debug("Request to get Freecom_pfic : {}", id);
        Freecom_pfic freecom_pfic = freecom_pficRepository.findOne(id);
        return freecom_pfic;
    }

    /**
     *  Delete the  freecom_pfic by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_pfic : {}", id);
        freecom_pficRepository.delete(id);
    }
}
