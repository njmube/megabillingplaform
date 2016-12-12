package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_dataenajenantecopscService;
import org.megapractical.billingplatform.domain.Freecom_dataenajenantecopsc;
import org.megapractical.billingplatform.repository.Freecom_dataenajenantecopscRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_dataenajenantecopsc.
 */
@Service
@Transactional
public class Freecom_dataenajenantecopscServiceImpl implements Freecom_dataenajenantecopscService{

    private final Logger log = LoggerFactory.getLogger(Freecom_dataenajenantecopscServiceImpl.class);
    
    @Inject
    private Freecom_dataenajenantecopscRepository freecom_dataenajenantecopscRepository;
    
    /**
     * Save a freecom_dataenajenantecopsc.
     * 
     * @param freecom_dataenajenantecopsc the entity to save
     * @return the persisted entity
     */
    public Freecom_dataenajenantecopsc save(Freecom_dataenajenantecopsc freecom_dataenajenantecopsc) {
        log.debug("Request to save Freecom_dataenajenantecopsc : {}", freecom_dataenajenantecopsc);
        Freecom_dataenajenantecopsc result = freecom_dataenajenantecopscRepository.save(freecom_dataenajenantecopsc);
        return result;
    }

    /**
     *  Get all the freecom_dataenajenantecopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_dataenajenantecopsc> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_dataenajenantecopscs");
        Page<Freecom_dataenajenantecopsc> result = freecom_dataenajenantecopscRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_dataenajenantecopsc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_dataenajenantecopsc findOne(Long id) {
        log.debug("Request to get Freecom_dataenajenantecopsc : {}", id);
        Freecom_dataenajenantecopsc freecom_dataenajenantecopsc = freecom_dataenajenantecopscRepository.findOne(id);
        return freecom_dataenajenantecopsc;
    }

    /**
     *  Delete the  freecom_dataenajenantecopsc by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_dataenajenantecopsc : {}", id);
        freecom_dataenajenantecopscRepository.delete(id);
    }
}
