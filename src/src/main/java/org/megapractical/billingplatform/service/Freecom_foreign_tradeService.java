package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_foreign_trade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_foreign_trade.
 */
public interface Freecom_foreign_tradeService {

    /**
     * Save a freecom_foreign_trade.
     * 
     * @param freecom_foreign_trade the entity to save
     * @return the persisted entity
     */
    Freecom_foreign_trade save(Freecom_foreign_trade freecom_foreign_trade);

    /**
     *  Get all the freecom_foreign_trades.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_foreign_trade> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_foreign_trade.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_foreign_trade findOne(Long id);

    /**
     *  Delete the "id" freecom_foreign_trade.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
