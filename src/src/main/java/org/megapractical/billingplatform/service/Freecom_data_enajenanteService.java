package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_data_enajenante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_data_enajenante.
 */
public interface Freecom_data_enajenanteService {

    /**
     * Save a freecom_data_enajenante.
     * 
     * @param freecom_data_enajenante the entity to save
     * @return the persisted entity
     */
    Freecom_data_enajenante save(Freecom_data_enajenante freecom_data_enajenante);

    /**
     *  Get all the freecom_data_enajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_data_enajenante> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_data_enajenante.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_data_enajenante findOne(Long id);

    /**
     *  Delete the "id" freecom_data_enajenante.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
