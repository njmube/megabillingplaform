package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Customs_info_part;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Customs_info_part.
 */
public interface Customs_info_partService {

    /**
     * Save a customs_info_part.
     * 
     * @param customs_info_part the entity to save
     * @return the persisted entity
     */
    Customs_info_part save(Customs_info_part customs_info_part);

    /**
     *  Get all the customs_info_parts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Customs_info_part> findAll(Pageable pageable);

    /**
     *  Get the "id" customs_info_part.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Customs_info_part findOne(Long id);

    /**
     *  Delete the "id" customs_info_part.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
