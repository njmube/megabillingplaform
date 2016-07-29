package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_foreign_exchangeService;
import org.megapractical.billingplatform.domain.Freecom_foreign_exchange;
import org.megapractical.billingplatform.repository.Freecom_foreign_exchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_foreign_exchange.
 */
@Service
@Transactional
public class Freecom_foreign_exchangeServiceImpl implements Freecom_foreign_exchangeService{

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_exchangeServiceImpl.class);
    
    @Inject
    private Freecom_foreign_exchangeRepository freecom_foreign_exchangeRepository;
    
    /**
     * Save a freecom_foreign_exchange.
     * 
     * @param freecom_foreign_exchange the entity to save
     * @return the persisted entity
     */
    public Freecom_foreign_exchange save(Freecom_foreign_exchange freecom_foreign_exchange) {
        log.debug("Request to save Freecom_foreign_exchange : {}", freecom_foreign_exchange);
        Freecom_foreign_exchange result = freecom_foreign_exchangeRepository.save(freecom_foreign_exchange);
        return result;
    }

    /**
     *  Get all the freecom_foreign_exchanges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_foreign_exchange> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_foreign_exchanges");
        Page<Freecom_foreign_exchange> result = freecom_foreign_exchangeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_foreign_exchange by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_foreign_exchange findOne(Long id) {
        log.debug("Request to get Freecom_foreign_exchange : {}", id);
        Freecom_foreign_exchange freecom_foreign_exchange = freecom_foreign_exchangeRepository.findOne(id);
        return freecom_foreign_exchange;
    }

    /**
     *  Delete the  freecom_foreign_exchange by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_foreign_exchange : {}", id);
        freecom_foreign_exchangeRepository.delete(id);
    }
}
