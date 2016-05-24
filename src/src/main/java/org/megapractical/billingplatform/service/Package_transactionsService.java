package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Package_transactions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Package_transactions.
 */
public interface Package_transactionsService {

    /**
     * Save a package_transactions.
     * 
     * @param package_transactions the entity to save
     * @return the persisted entity
     */
    Package_transactions save(Package_transactions package_transactions);

    /**
     *  Get all the package_transactions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Package_transactions> findAll(Pageable pageable);

    /**
     *  Get the "id" package_transactions.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Package_transactions findOne(Long id);

    /**
     *  Delete the "id" package_transactions.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
