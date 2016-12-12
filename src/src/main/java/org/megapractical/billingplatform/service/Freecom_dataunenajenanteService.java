package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_dataunenajenante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_dataunenajenante.
 */
public interface Freecom_dataunenajenanteService {

    /**
     * Save a freecom_dataunenajenante.
     * 
     * @param freecom_dataunenajenante the entity to save
     * @return the persisted entity
     */
    Freecom_dataunenajenante save(Freecom_dataunenajenante freecom_dataunenajenante);

    /**
     *  Get all the freecom_dataunenajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_dataunenajenante> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_dataunenajenante.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_dataunenajenante findOne(Long id);

    /**
     *  Delete the "id" freecom_dataunenajenante.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
