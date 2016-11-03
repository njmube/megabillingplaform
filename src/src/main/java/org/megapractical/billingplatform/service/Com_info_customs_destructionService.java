package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_info_customs_destruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_info_customs_destruction.
 */
public interface Com_info_customs_destructionService {

    /**
     * Save a com_info_customs_destruction.
     * 
     * @param com_info_customs_destruction the entity to save
     * @return the persisted entity
     */
    Com_info_customs_destruction save(Com_info_customs_destruction com_info_customs_destruction);

    /**
     *  Get all the com_info_customs_destructions.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_info_customs_destruction> findAll(Pageable pageable);

    /**
     *  Get the "id" com_info_customs_destruction.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_info_customs_destruction findOne(Long id);

    /**
     *  Delete the "id" com_info_customs_destruction.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
