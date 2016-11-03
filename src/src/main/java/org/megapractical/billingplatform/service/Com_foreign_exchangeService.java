package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_foreign_exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_foreign_exchange.
 */
public interface Com_foreign_exchangeService {

    /**
     * Save a com_foreign_exchange.
     * 
     * @param com_foreign_exchange the entity to save
     * @return the persisted entity
     */
    Com_foreign_exchange save(Com_foreign_exchange com_foreign_exchange);

    /**
     *  Get all the com_foreign_exchanges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_foreign_exchange> findAll(Pageable pageable);

    /**
     *  Get the "id" com_foreign_exchange.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_foreign_exchange findOne(Long id);

    /**
     *  Delete the "id" com_foreign_exchange.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
