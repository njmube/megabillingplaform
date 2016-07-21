package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_committee_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_committee_type.
 */
public interface C_committee_typeService {

    /**
     * Save a c_committee_type.
     * 
     * @param c_committee_type the entity to save
     * @return the persisted entity
     */
    C_committee_type save(C_committee_type c_committee_type);

    /**
     *  Get all the c_committee_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_committee_type> findAll(Pageable pageable);

    /**
     *  Get the "id" c_committee_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_committee_type findOne(Long id);

    /**
     *  Delete the "id" c_committee_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
