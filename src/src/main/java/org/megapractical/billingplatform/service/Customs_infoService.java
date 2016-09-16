package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Customs_info;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Customs_info.
 */
public interface Customs_infoService {

    /**
     * Save a customs_info.
     * 
     * @param customs_info the entity to save
     * @return the persisted entity
     */
    Customs_info save(Customs_info customs_info);

    /**
     *  Get all the customs_infos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Customs_info> findAll(Pageable pageable);

    /**
     *  Get the "id" customs_info.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Customs_info findOne(Long id);

    /**
     *  Delete the "id" customs_info.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
