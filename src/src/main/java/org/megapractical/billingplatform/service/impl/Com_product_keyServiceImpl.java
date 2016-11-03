package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_product_keyService;
import org.megapractical.billingplatform.domain.Com_product_key;
import org.megapractical.billingplatform.repository.Com_product_keyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_product_key.
 */
@Service
@Transactional
public class Com_product_keyServiceImpl implements Com_product_keyService{

    private final Logger log = LoggerFactory.getLogger(Com_product_keyServiceImpl.class);
    
    @Inject
    private Com_product_keyRepository com_product_keyRepository;
    
    /**
     * Save a com_product_key.
     * 
     * @param com_product_key the entity to save
     * @return the persisted entity
     */
    public Com_product_key save(Com_product_key com_product_key) {
        log.debug("Request to save Com_product_key : {}", com_product_key);
        Com_product_key result = com_product_keyRepository.save(com_product_key);
        return result;
    }

    /**
     *  Get all the com_product_keys.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_product_key> findAll(Pageable pageable) {
        log.debug("Request to get all Com_product_keys");
        Page<Com_product_key> result = com_product_keyRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_product_key by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_product_key findOne(Long id) {
        log.debug("Request to get Com_product_key : {}", id);
        Com_product_key com_product_key = com_product_keyRepository.findOne(id);
        return com_product_key;
    }

    /**
     *  Delete the  com_product_key by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_product_key : {}", id);
        com_product_keyRepository.delete(id);
    }
}
