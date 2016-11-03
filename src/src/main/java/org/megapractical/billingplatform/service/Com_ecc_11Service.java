package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_ecc_11;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_ecc_11.
 */
public interface Com_ecc_11Service {

    /**
     * Save a com_ecc_11.
     * 
     * @param com_ecc_11 the entity to save
     * @return the persisted entity
     */
    Com_ecc_11 save(Com_ecc_11 com_ecc_11);

    /**
     *  Get all the com_ecc_11S.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_ecc_11> findAll(Pageable pageable);

    /**
     *  Get the "id" com_ecc_11.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_ecc_11 findOne(Long id);

    /**
     *  Delete the "id" com_ecc_11.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
