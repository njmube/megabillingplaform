package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_product_keyService;
import org.megapractical.billingplatform.domain.Freecom_product_key;
import org.megapractical.billingplatform.repository.Freecom_product_keyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_product_key.
 */
@Service
@Transactional
public class Freecom_product_keyServiceImpl implements Freecom_product_keyService{

    private final Logger log = LoggerFactory.getLogger(Freecom_product_keyServiceImpl.class);
    
    @Inject
    private Freecom_product_keyRepository freecom_product_keyRepository;
    
    /**
     * Save a freecom_product_key.
     * 
     * @param freecom_product_key the entity to save
     * @return the persisted entity
     */
    public Freecom_product_key save(Freecom_product_key freecom_product_key) {
        log.debug("Request to save Freecom_product_key : {}", freecom_product_key);
        Freecom_product_key result = freecom_product_keyRepository.save(freecom_product_key);
        return result;
    }

    /**
     *  Get all the freecom_product_keys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_product_key> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_product_keys");
        Page<Freecom_product_key> result = freecom_product_keyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_product_key by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_product_key findOne(Long id) {
        log.debug("Request to get Freecom_product_key : {}", id);
        Freecom_product_key freecom_product_key = freecom_product_keyRepository.findOne(id);
        return freecom_product_key;
    }

    /**
     *  Delete the  freecom_product_key by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_product_key : {}", id);
        freecom_product_keyRepository.delete(id);
    }
}
