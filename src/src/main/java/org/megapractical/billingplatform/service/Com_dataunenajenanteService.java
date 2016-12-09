package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_dataunenajenante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_dataunenajenante.
 */
public interface Com_dataunenajenanteService {

    /**
     * Save a com_dataunenajenante.
     * 
     * @param com_dataunenajenante the entity to save
     * @return the persisted entity
     */
    Com_dataunenajenante save(Com_dataunenajenante com_dataunenajenante);

    /**
     *  Get all the com_dataunenajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_dataunenajenante> findAll(Pageable pageable);

    /**
     *  Get the "id" com_dataunenajenante.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_dataunenajenante findOne(Long id);

    /**
     *  Delete the "id" com_dataunenajenante.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
