package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_dataenajenantecopsc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_dataenajenantecopsc.
 */
public interface Com_dataenajenantecopscService {

    /**
     * Save a com_dataenajenantecopsc.
     * 
     * @param com_dataenajenantecopsc the entity to save
     * @return the persisted entity
     */
    Com_dataenajenantecopsc save(Com_dataenajenantecopsc com_dataenajenantecopsc);

    /**
     *  Get all the com_dataenajenantecopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_dataenajenantecopsc> findAll(Pageable pageable);

    /**
     *  Get the "id" com_dataenajenantecopsc.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_dataenajenantecopsc findOne(Long id);

    /**
     *  Delete the "id" com_dataenajenantecopsc.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
