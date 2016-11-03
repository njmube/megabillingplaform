package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_foreign_tradeService;
import org.megapractical.billingplatform.domain.Com_foreign_trade;
import org.megapractical.billingplatform.repository.Com_foreign_tradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_foreign_trade.
 */
@Service
@Transactional
public class Com_foreign_tradeServiceImpl implements Com_foreign_tradeService{

    private final Logger log = LoggerFactory.getLogger(Com_foreign_tradeServiceImpl.class);
    
    @Inject
    private Com_foreign_tradeRepository com_foreign_tradeRepository;
    
    /**
     * Save a com_foreign_trade.
     * 
     * @param com_foreign_trade the entity to save
     * @return the persisted entity
     */
    public Com_foreign_trade save(Com_foreign_trade com_foreign_trade) {
        log.debug("Request to save Com_foreign_trade : {}", com_foreign_trade);
        Com_foreign_trade result = com_foreign_tradeRepository.save(com_foreign_trade);
        return result;
    }

    /**
     *  Get all the com_foreign_trades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_foreign_trade> findAll(Pageable pageable) {
        log.debug("Request to get all Com_foreign_trades");
        Page<Com_foreign_trade> result = com_foreign_tradeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_foreign_trade by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_foreign_trade findOne(Long id) {
        log.debug("Request to get Com_foreign_trade : {}", id);
        Com_foreign_trade com_foreign_trade = com_foreign_tradeRepository.findOne(id);
        return com_foreign_trade;
    }

    /**
     *  Delete the  com_foreign_trade by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_foreign_trade : {}", id);
        com_foreign_tradeRepository.delete(id);
    }
}
