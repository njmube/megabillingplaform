package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_dataunacquiringService;
import org.megapractical.billingplatform.domain.Freecom_dataunacquiring;
import org.megapractical.billingplatform.repository.Freecom_dataunacquiringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_dataunacquiring.
 */
@Service
@Transactional
public class Freecom_dataunacquiringServiceImpl implements Freecom_dataunacquiringService{

    private final Logger log = LoggerFactory.getLogger(Freecom_dataunacquiringServiceImpl.class);
    
    @Inject
    private Freecom_dataunacquiringRepository freecom_dataunacquiringRepository;
    
    /**
     * Save a freecom_dataunacquiring.
     * 
     * @param freecom_dataunacquiring the entity to save
     * @return the persisted entity
     */
    public Freecom_dataunacquiring save(Freecom_dataunacquiring freecom_dataunacquiring) {
        log.debug("Request to save Freecom_dataunacquiring : {}", freecom_dataunacquiring);
        Freecom_dataunacquiring result = freecom_dataunacquiringRepository.save(freecom_dataunacquiring);
        return result;
    }

    /**
     *  Get all the freecom_dataunacquirings.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_dataunacquiring> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_dataunacquirings");
        Page<Freecom_dataunacquiring> result = freecom_dataunacquiringRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_dataunacquiring by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_dataunacquiring findOne(Long id) {
        log.debug("Request to get Freecom_dataunacquiring : {}", id);
        Freecom_dataunacquiring freecom_dataunacquiring = freecom_dataunacquiringRepository.findOne(id);
        return freecom_dataunacquiring;
    }

    /**
     *  Delete the  freecom_dataunacquiring by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_dataunacquiring : {}", id);
        freecom_dataunacquiringRepository.delete(id);
    }
}
