package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Type_taxpayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Type_taxpayer.
 */
public interface Type_taxpayerService {

    /**
     * Save a type_taxpayer.
     * 
     * @param type_taxpayer the entity to save
     * @return the persisted entity
     */
    Type_taxpayer save(Type_taxpayer type_taxpayer);

    /**
     *  Get all the type_taxpayers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Type_taxpayer> findAll(Pageable pageable);

    /**
     *  Get the "id" type_taxpayer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Type_taxpayer findOne(Long id);

    /**
     *  Delete the "id" type_taxpayer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
