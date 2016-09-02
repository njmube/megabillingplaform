package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Branch_office;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Branch_office.
 */
public interface Branch_officeService {

    /**
     * Save a branch_office.
     * 
     * @param branch_office the entity to save
     * @return the persisted entity
     */
    Branch_office save(Branch_office branch_office);

    /**
     *  Get all the branch_offices.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Branch_office> findAll(Pageable pageable);

    /**
     *  Get the "id" branch_office.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Branch_office findOne(Long id);

    /**
     *  Delete the "id" branch_office.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
