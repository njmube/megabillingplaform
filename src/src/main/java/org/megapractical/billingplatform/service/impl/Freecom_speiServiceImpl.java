package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_speiService;
import org.megapractical.billingplatform.domain.Freecom_spei;
import org.megapractical.billingplatform.repository.Freecom_speiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_spei.
 */
@Service
@Transactional
public class Freecom_speiServiceImpl implements Freecom_speiService{

    private final Logger log = LoggerFactory.getLogger(Freecom_speiServiceImpl.class);
    
    @Inject
    private Freecom_speiRepository freecom_speiRepository;
    
    /**
     * Save a freecom_spei.
     * 
     * @param freecom_spei the entity to save
     * @return the persisted entity
     */
    public Freecom_spei save(Freecom_spei freecom_spei) {
        log.debug("Request to save Freecom_spei : {}", freecom_spei);
        Freecom_spei result = freecom_speiRepository.save(freecom_spei);
        return result;
    }

    /**
     *  Get all the freecom_speis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_spei> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_speis");
        Page<Freecom_spei> result = freecom_speiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_spei by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_spei findOne(Long id) {
        log.debug("Request to get Freecom_spei : {}", id);
        Freecom_spei freecom_spei = freecom_speiRepository.findOne(id);
        return freecom_spei;
    }

    /**
     *  Delete the  freecom_spei by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_spei : {}", id);
        freecom_speiRepository.delete(id);
    }
}
