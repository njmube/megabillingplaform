package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Public_notaries_federal_entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Public_notaries_federal_entity.
 */
public interface Public_notaries_federal_entityService {

    /**
     * Save a public_notaries_federal_entity.
     * 
     * @param public_notaries_federal_entity the entity to save
     * @return the persisted entity
     */
    Public_notaries_federal_entity save(Public_notaries_federal_entity public_notaries_federal_entity);

    /**
     *  Get all the public_notaries_federal_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Public_notaries_federal_entity> findAll(Pageable pageable);

    /**
     *  Get the "id" public_notaries_federal_entity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Public_notaries_federal_entity findOne(Long id);

    /**
     *  Delete the "id" public_notaries_federal_entity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
