package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Committee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Committee.
 */
public interface CommitteeService {

    /**
     * Save a committee.
     * 
     * @param committee the entity to save
     * @return the persisted entity
     */
    Committee save(Committee committee);

    /**
     *  Get all the committees.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Committee> findAll(Pageable pageable);

    /**
     *  Get the "id" committee.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Committee findOne(Long id);

    /**
     *  Delete the "id" committee.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
