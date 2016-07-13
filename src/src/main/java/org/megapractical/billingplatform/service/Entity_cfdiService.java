package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Entity_cfdi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Entity_cfdi.
 */
public interface Entity_cfdiService {

    /**
     * Save a entity_cfdi.
     * 
     * @param entity_cfdi the entity to save
     * @return the persisted entity
     */
    Entity_cfdi save(Entity_cfdi entity_cfdi);

    /**
     *  Get all the entity_cfdis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Entity_cfdi> findAll(Pageable pageable);

    /**
     *  Get the "id" entity_cfdi.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Entity_cfdi findOne(Long id);

    /**
     *  Delete the "id" entity_cfdi.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
