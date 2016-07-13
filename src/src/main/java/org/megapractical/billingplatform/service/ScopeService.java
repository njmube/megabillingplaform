package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Scope.
 */
public interface ScopeService {

    /**
     * Save a scope.
     * 
     * @param scope the entity to save
     * @return the persisted entity
     */
    Scope save(Scope scope);

    /**
     *  Get all the scopes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Scope> findAll(Pageable pageable);

    /**
     *  Get the "id" scope.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Scope findOne(Long id);

    /**
     *  Delete the "id" scope.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
