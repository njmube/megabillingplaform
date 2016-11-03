package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_foreign_exchangeService;
import org.megapractical.billingplatform.domain.Com_foreign_exchange;
import org.megapractical.billingplatform.repository.Com_foreign_exchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_foreign_exchange.
 */
@Service
@Transactional
public class Com_foreign_exchangeServiceImpl implements Com_foreign_exchangeService{

    private final Logger log = LoggerFactory.getLogger(Com_foreign_exchangeServiceImpl.class);
    
    @Inject
    private Com_foreign_exchangeRepository com_foreign_exchangeRepository;
    
    /**
     * Save a com_foreign_exchange.
     * 
     * @param com_foreign_exchange the entity to save
     * @return the persisted entity
     */
    public Com_foreign_exchange save(Com_foreign_exchange com_foreign_exchange) {
        log.debug("Request to save Com_foreign_exchange : {}", com_foreign_exchange);
        Com_foreign_exchange result = com_foreign_exchangeRepository.save(com_foreign_exchange);
        return result;
    }

    /**
     *  Get all the com_foreign_exchanges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_foreign_exchange> findAll(Pageable pageable) {
        log.debug("Request to get all Com_foreign_exchanges");
        Page<Com_foreign_exchange> result = com_foreign_exchangeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_foreign_exchange by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_foreign_exchange findOne(Long id) {
        log.debug("Request to get Com_foreign_exchange : {}", id);
        Com_foreign_exchange com_foreign_exchange = com_foreign_exchangeRepository.findOne(id);
        return com_foreign_exchange;
    }

    /**
     *  Delete the  com_foreign_exchange by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_foreign_exchange : {}", id);
        com_foreign_exchangeRepository.delete(id);
    }
}
