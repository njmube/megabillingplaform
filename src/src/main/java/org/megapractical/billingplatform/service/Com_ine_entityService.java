package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_ine_entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_ine_entity.
 */
public interface Com_ine_entityService {

    /**
     * Save a com_ine_entity.
     * 
     * @param com_ine_entity the entity to save
     * @return the persisted entity
     */
    Com_ine_entity save(Com_ine_entity com_ine_entity);

    /**
     *  Get all the com_ine_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_ine_entity> findAll(Pageable pageable);

    /**
     *  Get the "id" com_ine_entity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_ine_entity findOne(Long id);

    /**
     *  Delete the "id" com_ine_entity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
