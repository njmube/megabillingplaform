package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_beneficiary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_beneficiary.
 */
public interface Freecom_beneficiaryService {

    /**
     * Save a freecom_beneficiary.
     * 
     * @param freecom_beneficiary the entity to save
     * @return the persisted entity
     */
    Freecom_beneficiary save(Freecom_beneficiary freecom_beneficiary);

    /**
     *  Get all the freecom_beneficiaries.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_beneficiary> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_beneficiary.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_beneficiary findOne(Long id);

    /**
     *  Delete the "id" freecom_beneficiary.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
