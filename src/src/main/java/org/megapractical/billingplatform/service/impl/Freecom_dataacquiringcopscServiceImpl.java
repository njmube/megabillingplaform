package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_dataacquiringcopscService;
import org.megapractical.billingplatform.domain.Freecom_dataacquiringcopsc;
import org.megapractical.billingplatform.repository.Freecom_dataacquiringcopscRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_dataacquiringcopsc.
 */
@Service
@Transactional
public class Freecom_dataacquiringcopscServiceImpl implements Freecom_dataacquiringcopscService{

    private final Logger log = LoggerFactory.getLogger(Freecom_dataacquiringcopscServiceImpl.class);
    
    @Inject
    private Freecom_dataacquiringcopscRepository freecom_dataacquiringcopscRepository;
    
    /**
     * Save a freecom_dataacquiringcopsc.
     * 
     * @param freecom_dataacquiringcopsc the entity to save
     * @return the persisted entity
     */
    public Freecom_dataacquiringcopsc save(Freecom_dataacquiringcopsc freecom_dataacquiringcopsc) {
        log.debug("Request to save Freecom_dataacquiringcopsc : {}", freecom_dataacquiringcopsc);
        Freecom_dataacquiringcopsc result = freecom_dataacquiringcopscRepository.save(freecom_dataacquiringcopsc);
        return result;
    }

    /**
     *  Get all the freecom_dataacquiringcopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_dataacquiringcopsc> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_dataacquiringcopscs");
        Page<Freecom_dataacquiringcopsc> result = freecom_dataacquiringcopscRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_dataacquiringcopsc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_dataacquiringcopsc findOne(Long id) {
        log.debug("Request to get Freecom_dataacquiringcopsc : {}", id);
        Freecom_dataacquiringcopsc freecom_dataacquiringcopsc = freecom_dataacquiringcopscRepository.findOne(id);
        return freecom_dataacquiringcopsc;
    }

    /**
     *  Delete the  freecom_dataacquiringcopsc by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_dataacquiringcopsc : {}", id);
        freecom_dataacquiringcopscRepository.delete(id);
    }
}
