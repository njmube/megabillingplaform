package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_storeroom_paybillService;
import org.megapractical.billingplatform.domain.Com_storeroom_paybill;
import org.megapractical.billingplatform.repository.Com_storeroom_paybillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_storeroom_paybill.
 */
@Service
@Transactional
public class Com_storeroom_paybillServiceImpl implements Com_storeroom_paybillService{

    private final Logger log = LoggerFactory.getLogger(Com_storeroom_paybillServiceImpl.class);
    
    @Inject
    private Com_storeroom_paybillRepository com_storeroom_paybillRepository;
    
    /**
     * Save a com_storeroom_paybill.
     * 
     * @param com_storeroom_paybill the entity to save
     * @return the persisted entity
     */
    public Com_storeroom_paybill save(Com_storeroom_paybill com_storeroom_paybill) {
        log.debug("Request to save Com_storeroom_paybill : {}", com_storeroom_paybill);
        Com_storeroom_paybill result = com_storeroom_paybillRepository.save(com_storeroom_paybill);
        return result;
    }

    /**
     *  Get all the com_storeroom_paybills.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_storeroom_paybill> findAll(Pageable pageable) {
        log.debug("Request to get all Com_storeroom_paybills");
        Page<Com_storeroom_paybill> result = com_storeroom_paybillRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_storeroom_paybill by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_storeroom_paybill findOne(Long id) {
        log.debug("Request to get Com_storeroom_paybill : {}", id);
        Com_storeroom_paybill com_storeroom_paybill = com_storeroom_paybillRepository.findOne(id);
        return com_storeroom_paybill;
    }

    /**
     *  Delete the  com_storeroom_paybill by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_storeroom_paybill : {}", id);
        com_storeroom_paybillRepository.delete(id);
    }
}
