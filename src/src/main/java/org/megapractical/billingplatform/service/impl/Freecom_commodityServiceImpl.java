package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_commodityService;
import org.megapractical.billingplatform.domain.Freecom_commodity;
import org.megapractical.billingplatform.repository.Freecom_commodityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_commodity.
 */
@Service
@Transactional
public class Freecom_commodityServiceImpl implements Freecom_commodityService{

    private final Logger log = LoggerFactory.getLogger(Freecom_commodityServiceImpl.class);
    
    @Inject
    private Freecom_commodityRepository freecom_commodityRepository;
    
    /**
     * Save a freecom_commodity.
     * 
     * @param freecom_commodity the entity to save
     * @return the persisted entity
     */
    public Freecom_commodity save(Freecom_commodity freecom_commodity) {
        log.debug("Request to save Freecom_commodity : {}", freecom_commodity);
        Freecom_commodity result = freecom_commodityRepository.save(freecom_commodity);
        return result;
    }

    /**
     *  Get all the freecom_commodities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_commodity> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_commodities");
        Page<Freecom_commodity> result = freecom_commodityRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_commodity by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_commodity findOne(Long id) {
        log.debug("Request to get Freecom_commodity : {}", id);
        Freecom_commodity freecom_commodity = freecom_commodityRepository.findOne(id);
        return freecom_commodity;
    }

    /**
     *  Delete the  freecom_commodity by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_commodity : {}", id);
        freecom_commodityRepository.delete(id);
    }
}
