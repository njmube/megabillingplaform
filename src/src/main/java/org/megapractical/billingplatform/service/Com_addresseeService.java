package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_addressee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_addressee.
 */
public interface Com_addresseeService {

    /**
     * Save a com_addressee.
     * 
     * @param com_addressee the entity to save
     * @return the persisted entity
     */
    Com_addressee save(Com_addressee com_addressee);

    /**
     *  Get all the com_addressees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_addressee> findAll(Pageable pageable);

    /**
     *  Get the "id" com_addressee.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_addressee findOne(Long id);

    /**
     *  Delete the "id" com_addressee.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
