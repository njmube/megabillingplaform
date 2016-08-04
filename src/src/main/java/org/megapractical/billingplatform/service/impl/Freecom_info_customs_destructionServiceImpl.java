package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_info_customs_destructionService;
import org.megapractical.billingplatform.domain.Freecom_info_customs_destruction;
import org.megapractical.billingplatform.repository.Freecom_info_customs_destructionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_info_customs_destruction.
 */
@Service
@Transactional
public class Freecom_info_customs_destructionServiceImpl implements Freecom_info_customs_destructionService{

    private final Logger log = LoggerFactory.getLogger(Freecom_info_customs_destructionServiceImpl.class);
    
    @Inject
    private Freecom_info_customs_destructionRepository freecom_info_customs_destructionRepository;
    
    /**
     * Save a freecom_info_customs_destruction.
     * 
     * @param freecom_info_customs_destruction the entity to save
     * @return the persisted entity
     */
    public Freecom_info_customs_destruction save(Freecom_info_customs_destruction freecom_info_customs_destruction) {
        log.debug("Request to save Freecom_info_customs_destruction : {}", freecom_info_customs_destruction);
        Freecom_info_customs_destruction result = freecom_info_customs_destructionRepository.save(freecom_info_customs_destruction);
        return result;
    }

    /**
     *  Get all the freecom_info_customs_destructions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_info_customs_destruction> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_info_customs_destructions");
        Page<Freecom_info_customs_destruction> result = freecom_info_customs_destructionRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_info_customs_destruction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_info_customs_destruction findOne(Long id) {
        log.debug("Request to get Freecom_info_customs_destruction : {}", id);
        Freecom_info_customs_destruction freecom_info_customs_destruction = freecom_info_customs_destructionRepository.findOne(id);
        return freecom_info_customs_destruction;
    }

    /**
     *  Delete the  freecom_info_customs_destruction by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_info_customs_destruction : {}", id);
        freecom_info_customs_destructionRepository.delete(id);
    }
}
