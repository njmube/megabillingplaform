package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_data_enajenante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_data_enajenante.
 */
public interface Com_data_enajenanteService {

    /**
     * Save a com_data_enajenante.
     * 
     * @param com_data_enajenante the entity to save
     * @return the persisted entity
     */
    Com_data_enajenante save(Com_data_enajenante com_data_enajenante);

    /**
     *  Get all the com_data_enajenantes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_data_enajenante> findAll(Pageable pageable);

    /**
     *  Get the "id" com_data_enajenante.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_data_enajenante findOne(Long id);

    /**
     *  Delete the "id" com_data_enajenante.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
