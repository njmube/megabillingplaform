package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_dataacquiringcopsc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_dataacquiringcopsc.
 */
public interface Com_dataacquiringcopscService {

    /**
     * Save a com_dataacquiringcopsc.
     * 
     * @param com_dataacquiringcopsc the entity to save
     * @return the persisted entity
     */
    Com_dataacquiringcopsc save(Com_dataacquiringcopsc com_dataacquiringcopsc);

    /**
     *  Get all the com_dataacquiringcopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_dataacquiringcopsc> findAll(Pageable pageable);

    /**
     *  Get the "id" com_dataacquiringcopsc.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_dataacquiringcopsc findOne(Long id);

    /**
     *  Delete the "id" com_dataacquiringcopsc.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
