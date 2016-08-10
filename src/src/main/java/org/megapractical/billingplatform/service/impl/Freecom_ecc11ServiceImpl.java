package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_ecc11Service;
import org.megapractical.billingplatform.domain.Freecom_ecc11;
import org.megapractical.billingplatform.repository.Freecom_ecc11Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_ecc11.
 */
@Service
@Transactional
public class Freecom_ecc11ServiceImpl implements Freecom_ecc11Service{

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11ServiceImpl.class);
    
    @Inject
    private Freecom_ecc11Repository freecom_ecc11Repository;
    
    /**
     * Save a freecom_ecc11.
     * 
     * @param freecom_ecc11 the entity to save
     * @return the persisted entity
     */
    public Freecom_ecc11 save(Freecom_ecc11 freecom_ecc11) {
        log.debug("Request to save Freecom_ecc11 : {}", freecom_ecc11);
        Freecom_ecc11 result = freecom_ecc11Repository.save(freecom_ecc11);
        return result;
    }

    /**
     *  Get all the freecom_ecc11S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_ecc11> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_ecc11S");
        Page<Freecom_ecc11> result = freecom_ecc11Repository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_ecc11 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_ecc11 findOne(Long id) {
        log.debug("Request to get Freecom_ecc11 : {}", id);
        Freecom_ecc11 freecom_ecc11 = freecom_ecc11Repository.findOne(id);
        return freecom_ecc11;
    }

    /**
     *  Delete the  freecom_ecc11 by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_ecc11 : {}", id);
        freecom_ecc11Repository.delete(id);
    }
}
