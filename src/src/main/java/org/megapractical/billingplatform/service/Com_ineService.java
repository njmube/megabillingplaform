package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_ine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_ine.
 */
public interface Com_ineService {

    /**
     * Save a com_ine.
     * 
     * @param com_ine the entity to save
     * @return the persisted entity
     */
    Com_ine save(Com_ine com_ine);

    /**
     *  Get all the com_ines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_ine> findAll(Pageable pageable);

    /**
     *  Get the "id" com_ine.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_ine findOne(Long id);

    /**
     *  Delete the "id" com_ine.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
