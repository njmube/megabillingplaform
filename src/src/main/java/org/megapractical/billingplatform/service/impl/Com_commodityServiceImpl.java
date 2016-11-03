package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_commodityService;
import org.megapractical.billingplatform.domain.Com_commodity;
import org.megapractical.billingplatform.repository.Com_commodityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_commodity.
 */
@Service
@Transactional
public class Com_commodityServiceImpl implements Com_commodityService{

    private final Logger log = LoggerFactory.getLogger(Com_commodityServiceImpl.class);
    
    @Inject
    private Com_commodityRepository com_commodityRepository;
    
    /**
     * Save a com_commodity.
     * 
     * @param com_commodity the entity to save
     * @return the persisted entity
     */
    public Com_commodity save(Com_commodity com_commodity) {
        log.debug("Request to save Com_commodity : {}", com_commodity);
        Com_commodity result = com_commodityRepository.save(com_commodity);
        return result;
    }

    /**
     *  Get all the com_commodities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_commodity> findAll(Pageable pageable) {
        log.debug("Request to get all Com_commodities");
        Page<Com_commodity> result = com_commodityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_commodity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_commodity findOne(Long id) {
        log.debug("Request to get Com_commodity : {}", id);
        Com_commodity com_commodity = com_commodityRepository.findOne(id);
        return com_commodity;
    }

    /**
     *  Delete the  com_commodity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_commodity : {}", id);
        com_commodityRepository.delete(id);
    }
}
