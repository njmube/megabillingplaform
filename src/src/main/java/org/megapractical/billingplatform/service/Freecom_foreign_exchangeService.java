package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_foreign_exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_foreign_exchange.
 */
public interface Freecom_foreign_exchangeService {

    /**
     * Save a freecom_foreign_exchange.
     * 
     * @param freecom_foreign_exchange the entity to save
     * @return the persisted entity
     */
    Freecom_foreign_exchange save(Freecom_foreign_exchange freecom_foreign_exchange);

    /**
     *  Get all the freecom_foreign_exchanges.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_foreign_exchange> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_foreign_exchange.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_foreign_exchange findOne(Long id);

    /**
     *  Delete the "id" freecom_foreign_exchange.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
