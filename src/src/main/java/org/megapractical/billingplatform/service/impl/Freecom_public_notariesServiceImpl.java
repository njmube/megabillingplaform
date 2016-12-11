package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_public_notariesService;
import org.megapractical.billingplatform.domain.Freecom_public_notaries;
import org.megapractical.billingplatform.repository.Freecom_public_notariesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_public_notaries.
 */
@Service
@Transactional
public class Freecom_public_notariesServiceImpl implements Freecom_public_notariesService{

    private final Logger log = LoggerFactory.getLogger(Freecom_public_notariesServiceImpl.class);
    
    @Inject
    private Freecom_public_notariesRepository freecom_public_notariesRepository;
    
    /**
     * Save a freecom_public_notaries.
     * 
     * @param freecom_public_notaries the entity to save
     * @return the persisted entity
     */
    public Freecom_public_notaries save(Freecom_public_notaries freecom_public_notaries) {
        log.debug("Request to save Freecom_public_notaries : {}", freecom_public_notaries);
        Freecom_public_notaries result = freecom_public_notariesRepository.save(freecom_public_notaries);
        return result;
    }

    /**
     *  Get all the freecom_public_notaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_public_notaries> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_public_notaries");
        Page<Freecom_public_notaries> result = freecom_public_notariesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_public_notaries by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_public_notaries findOne(Long id) {
        log.debug("Request to get Freecom_public_notaries : {}", id);
        Freecom_public_notaries freecom_public_notaries = freecom_public_notariesRepository.findOne(id);
        return freecom_public_notaries;
    }

    /**
     *  Delete the  freecom_public_notaries by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_public_notaries : {}", id);
        freecom_public_notariesRepository.delete(id);
    }
}
