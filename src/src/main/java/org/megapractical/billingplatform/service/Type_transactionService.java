package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Type_transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Type_transaction.
 */
public interface Type_transactionService {

    /**
     * Save a type_transaction.
     * 
     * @param type_transaction the entity to save
     * @return the persisted entity
     */
    Type_transaction save(Type_transaction type_transaction);

    /**
     *  Get all the type_transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Type_transaction> findAll(Pageable pageable);

    /**
     *  Get the "id" type_transaction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Type_transaction findOne(Long id);

    /**
     *  Delete the "id" type_transaction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
