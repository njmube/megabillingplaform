package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_ecc_11Service;
import org.megapractical.billingplatform.domain.Com_ecc_11;
import org.megapractical.billingplatform.repository.Com_ecc_11Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_ecc_11.
 */
@Service
@Transactional
public class Com_ecc_11ServiceImpl implements Com_ecc_11Service{

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11ServiceImpl.class);
    
    @Inject
    private Com_ecc_11Repository com_ecc_11Repository;
    
    /**
     * Save a com_ecc_11.
     * 
     * @param com_ecc_11 the entity to save
     * @return the persisted entity
     */
    public Com_ecc_11 save(Com_ecc_11 com_ecc_11) {
        log.debug("Request to save Com_ecc_11 : {}", com_ecc_11);
        Com_ecc_11 result = com_ecc_11Repository.save(com_ecc_11);
        return result;
    }

    /**
     *  Get all the com_ecc_11S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_ecc_11> findAll(Pageable pageable) {
        log.debug("Request to get all Com_ecc_11S");
        Page<Com_ecc_11> result = com_ecc_11Repository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_ecc_11 by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_ecc_11 findOne(Long id) {
        log.debug("Request to get Com_ecc_11 : {}", id);
        Com_ecc_11 com_ecc_11 = com_ecc_11Repository.findOne(id);
        return com_ecc_11;
    }

    /**
     *  Delete the  com_ecc_11 by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_ecc_11 : {}", id);
        com_ecc_11Repository.delete(id);
    }
}
