package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_foreign_trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_foreign_trade.
 */
public interface Com_foreign_tradeService {

    /**
     * Save a com_foreign_trade.
     * 
     * @param com_foreign_trade the entity to save
     * @return the persisted entity
     */
    Com_foreign_trade save(Com_foreign_trade com_foreign_trade);

    /**
     *  Get all the com_foreign_trades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_foreign_trade> findAll(Pageable pageable);

    /**
     *  Get the "id" com_foreign_trade.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_foreign_trade findOne(Long id);

    /**
     *  Delete the "id" com_foreign_trade.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
