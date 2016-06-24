package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Free_customs_info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Free_customs_info.
 */
public interface Free_customs_infoService {

    /**
     * Save a free_customs_info.
     * 
     * @param free_customs_info the entity to save
     * @return the persisted entity
     */
    Free_customs_info save(Free_customs_info free_customs_info);

    /**
     *  Get all the free_customs_infos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Free_customs_info> findAll(Pageable pageable);

    /**
     *  Get the "id" free_customs_info.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Free_customs_info findOne(Long id);

    /**
     *  Delete the "id" free_customs_info.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
