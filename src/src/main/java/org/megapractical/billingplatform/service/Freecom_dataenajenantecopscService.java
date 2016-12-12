package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_dataenajenantecopsc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_dataenajenantecopsc.
 */
public interface Freecom_dataenajenantecopscService {

    /**
     * Save a freecom_dataenajenantecopsc.
     * 
     * @param freecom_dataenajenantecopsc the entity to save
     * @return the persisted entity
     */
    Freecom_dataenajenantecopsc save(Freecom_dataenajenantecopsc freecom_dataenajenantecopsc);

    /**
     *  Get all the freecom_dataenajenantecopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_dataenajenantecopsc> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_dataenajenantecopsc.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_dataenajenantecopsc findOne(Long id);

    /**
     *  Delete the "id" freecom_dataenajenantecopsc.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
