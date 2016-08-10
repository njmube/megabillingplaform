package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_spei_thirdService;
import org.megapractical.billingplatform.domain.Freecom_spei_third;
import org.megapractical.billingplatform.repository.Freecom_spei_thirdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_spei_third.
 */
@Service
@Transactional
public class Freecom_spei_thirdServiceImpl implements Freecom_spei_thirdService{

    private final Logger log = LoggerFactory.getLogger(Freecom_spei_thirdServiceImpl.class);
    
    @Inject
    private Freecom_spei_thirdRepository freecom_spei_thirdRepository;
    
    /**
     * Save a freecom_spei_third.
     * 
     * @param freecom_spei_third the entity to save
     * @return the persisted entity
     */
    public Freecom_spei_third save(Freecom_spei_third freecom_spei_third) {
        log.debug("Request to save Freecom_spei_third : {}", freecom_spei_third);
        Freecom_spei_third result = freecom_spei_thirdRepository.save(freecom_spei_third);
        return result;
    }

    /**
     *  Get all the freecom_spei_thirds.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_spei_third> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_spei_thirds");
        Page<Freecom_spei_third> result = freecom_spei_thirdRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_spei_third by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_spei_third findOne(Long id) {
        log.debug("Request to get Freecom_spei_third : {}", id);
        Freecom_spei_third freecom_spei_third = freecom_spei_thirdRepository.findOne(id);
        return freecom_spei_third;
    }

    /**
     *  Delete the  freecom_spei_third by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_spei_third : {}", id);
        freecom_spei_thirdRepository.delete(id);
    }
}
