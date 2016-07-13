package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_ineService;
import org.megapractical.billingplatform.domain.Freecom_ine;
import org.megapractical.billingplatform.repository.Freecom_ineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_ine.
 */
@Service
@Transactional
public class Freecom_ineServiceImpl implements Freecom_ineService{

    private final Logger log = LoggerFactory.getLogger(Freecom_ineServiceImpl.class);
    
    @Inject
    private Freecom_ineRepository freecom_ineRepository;
    
    /**
     * Save a freecom_ine.
     * 
     * @param freecom_ine the entity to save
     * @return the persisted entity
     */
    public Freecom_ine save(Freecom_ine freecom_ine) {
        log.debug("Request to save Freecom_ine : {}", freecom_ine);
        Freecom_ine result = freecom_ineRepository.save(freecom_ine);
        return result;
    }

    /**
     *  Get all the freecom_ines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_ine> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_ines");
        Page<Freecom_ine> result = freecom_ineRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_ine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_ine findOne(Long id) {
        log.debug("Request to get Freecom_ine : {}", id);
        Freecom_ine freecom_ine = freecom_ineRepository.findOne(id);
        return freecom_ine;
    }

    /**
     *  Delete the  freecom_ine by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_ine : {}", id);
        freecom_ineRepository.delete(id);
    }
}
