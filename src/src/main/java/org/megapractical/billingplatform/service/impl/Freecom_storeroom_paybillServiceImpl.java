package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_storeroom_paybillService;
import org.megapractical.billingplatform.domain.Freecom_storeroom_paybill;
import org.megapractical.billingplatform.repository.Freecom_storeroom_paybillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_storeroom_paybill.
 */
@Service
@Transactional
public class Freecom_storeroom_paybillServiceImpl implements Freecom_storeroom_paybillService{

    private final Logger log = LoggerFactory.getLogger(Freecom_storeroom_paybillServiceImpl.class);
    
    @Inject
    private Freecom_storeroom_paybillRepository freecom_storeroom_paybillRepository;
    
    /**
     * Save a freecom_storeroom_paybill.
     * 
     * @param freecom_storeroom_paybill the entity to save
     * @return the persisted entity
     */
    public Freecom_storeroom_paybill save(Freecom_storeroom_paybill freecom_storeroom_paybill) {
        log.debug("Request to save Freecom_storeroom_paybill : {}", freecom_storeroom_paybill);
        Freecom_storeroom_paybill result = freecom_storeroom_paybillRepository.save(freecom_storeroom_paybill);
        return result;
    }

    /**
     *  Get all the freecom_storeroom_paybills.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_storeroom_paybill> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_storeroom_paybills");
        Page<Freecom_storeroom_paybill> result = freecom_storeroom_paybillRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_storeroom_paybill by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_storeroom_paybill findOne(Long id) {
        log.debug("Request to get Freecom_storeroom_paybill : {}", id);
        Freecom_storeroom_paybill freecom_storeroom_paybill = freecom_storeroom_paybillRepository.findOne(id);
        return freecom_storeroom_paybill;
    }

    /**
     *  Delete the  freecom_storeroom_paybill by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_storeroom_paybill : {}", id);
        freecom_storeroom_paybillRepository.delete(id);
    }
}
