package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_apawService;
import org.megapractical.billingplatform.domain.Freecom_apaw;
import org.megapractical.billingplatform.repository.Freecom_apawRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_apaw.
 */
@Service
@Transactional
public class Freecom_apawServiceImpl implements Freecom_apawService{

    private final Logger log = LoggerFactory.getLogger(Freecom_apawServiceImpl.class);
    
    @Inject
    private Freecom_apawRepository freecom_apawRepository;
    
    /**
     * Save a freecom_apaw.
     * 
     * @param freecom_apaw the entity to save
     * @return the persisted entity
     */
    public Freecom_apaw save(Freecom_apaw freecom_apaw) {
        log.debug("Request to save Freecom_apaw : {}", freecom_apaw);
        Freecom_apaw result = freecom_apawRepository.save(freecom_apaw);
        return result;
    }

    /**
     *  Get all the freecom_apaws.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_apaw> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_apaws");
        Page<Freecom_apaw> result = freecom_apawRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_apaw by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_apaw findOne(Long id) {
        log.debug("Request to get Freecom_apaw : {}", id);
        Freecom_apaw freecom_apaw = freecom_apawRepository.findOne(id);
        return freecom_apaw;
    }

    /**
     *  Delete the  freecom_apaw by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_apaw : {}", id);
        freecom_apawRepository.delete(id);
    }
}
