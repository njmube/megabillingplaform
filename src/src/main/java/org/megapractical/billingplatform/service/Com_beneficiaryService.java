package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_beneficiary.
 */
public interface Com_beneficiaryService {

    /**
     * Save a com_beneficiary.
     * 
     * @param com_beneficiary the entity to save
     * @return the persisted entity
     */
    Com_beneficiary save(Com_beneficiary com_beneficiary);

    /**
     *  Get all the com_beneficiaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_beneficiary> findAll(Pageable pageable);

    /**
     *  Get the "id" com_beneficiary.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_beneficiary findOne(Long id);

    /**
     *  Delete the "id" com_beneficiary.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
