package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_ine_entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_ine_entity.
 */
public interface Freecom_ine_entityService {

    /**
     * Save a freecom_ine_entity.
     * 
     * @param freecom_ine_entity the entity to save
     * @return the persisted entity
     */
    Freecom_ine_entity save(Freecom_ine_entity freecom_ine_entity);

    /**
     *  Get all the freecom_ine_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_ine_entity> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_ine_entity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_ine_entity findOne(Long id);

    /**
     *  Delete the "id" freecom_ine_entity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
