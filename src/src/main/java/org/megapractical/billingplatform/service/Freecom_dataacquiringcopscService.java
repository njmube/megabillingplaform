package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_dataacquiringcopsc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_dataacquiringcopsc.
 */
public interface Freecom_dataacquiringcopscService {

    /**
     * Save a freecom_dataacquiringcopsc.
     * 
     * @param freecom_dataacquiringcopsc the entity to save
     * @return the persisted entity
     */
    Freecom_dataacquiringcopsc save(Freecom_dataacquiringcopsc freecom_dataacquiringcopsc);

    /**
     *  Get all the freecom_dataacquiringcopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_dataacquiringcopsc> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_dataacquiringcopsc.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_dataacquiringcopsc findOne(Long id);

    /**
     *  Delete the "id" freecom_dataacquiringcopsc.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
