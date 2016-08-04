package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_info_customs_destruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_info_customs_destruction.
 */
public interface Freecom_info_customs_destructionService {

    /**
     * Save a freecom_info_customs_destruction.
     * 
     * @param freecom_info_customs_destruction the entity to save
     * @return the persisted entity
     */
    Freecom_info_customs_destruction save(Freecom_info_customs_destruction freecom_info_customs_destruction);

    /**
     *  Get all the freecom_info_customs_destructions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_info_customs_destruction> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_info_customs_destruction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_info_customs_destruction findOne(Long id);

    /**
     *  Delete the "id" freecom_info_customs_destruction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
