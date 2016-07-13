package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_tar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_tar.
 */
public interface C_tarService {

    /**
     * Save a c_tar.
     * 
     * @param c_tar the entity to save
     * @return the persisted entity
     */
    C_tar save(C_tar c_tar);

    /**
     *  Get all the c_tars.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_tar> findAll(Pageable pageable);

    /**
     *  Get the "id" c_tar.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_tar findOne(Long id);

    /**
     *  Delete the "id" c_tar.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
