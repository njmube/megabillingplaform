package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_pn_federal_entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_pn_federal_entity.
 */
public interface C_pn_federal_entityService {

    /**
     * Save a c_pn_federal_entity.
     * 
     * @param c_pn_federal_entity the entity to save
     * @return the persisted entity
     */
    C_pn_federal_entity save(C_pn_federal_entity c_pn_federal_entity);

    /**
     *  Get all the c_pn_federal_entities.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_pn_federal_entity> findAll(Pageable pageable);

    /**
     *  Get the "id" c_pn_federal_entity.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_pn_federal_entity findOne(Long id);

    /**
     *  Delete the "id" c_pn_federal_entity.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
