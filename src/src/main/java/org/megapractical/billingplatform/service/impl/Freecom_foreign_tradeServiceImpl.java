package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_foreign_tradeService;
import org.megapractical.billingplatform.domain.Freecom_foreign_trade;
import org.megapractical.billingplatform.repository.Freecom_foreign_tradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_foreign_trade.
 */
@Service
@Transactional
public class Freecom_foreign_tradeServiceImpl implements Freecom_foreign_tradeService{

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_tradeServiceImpl.class);
    
    @Inject
    private Freecom_foreign_tradeRepository freecom_foreign_tradeRepository;
    
    /**
     * Save a freecom_foreign_trade.
     * 
     * @param freecom_foreign_trade the entity to save
     * @return the persisted entity
     */
    public Freecom_foreign_trade save(Freecom_foreign_trade freecom_foreign_trade) {
        log.debug("Request to save Freecom_foreign_trade : {}", freecom_foreign_trade);
        Freecom_foreign_trade result = freecom_foreign_tradeRepository.save(freecom_foreign_trade);
        return result;
    }

    /**
     *  Get all the freecom_foreign_trades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_foreign_trade> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_foreign_trades");
        Page<Freecom_foreign_trade> result = freecom_foreign_tradeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_foreign_trade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_foreign_trade findOne(Long id) {
        log.debug("Request to get Freecom_foreign_trade : {}", id);
        Freecom_foreign_trade freecom_foreign_trade = freecom_foreign_tradeRepository.findOne(id);
        return freecom_foreign_trade;
    }

    /**
     *  Delete the  freecom_foreign_trade by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_foreign_trade : {}", id);
        freecom_foreign_tradeRepository.delete(id);
    }
}
