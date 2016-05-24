package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_zip_code;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_zip_code.
 */
public interface C_zip_codeService {

    /**
     * Save a c_zip_code.
     * 
     * @param c_zip_code the entity to save
     * @return the persisted entity
     */
    C_zip_code save(C_zip_code c_zip_code);

    /**
     *  Get all the c_zip_codes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_zip_code> findAll(Pageable pageable);
    /**
     *  Get all the c_zip_codes where C_location is null.
     *  
     *  @return the list of entities
     */
    List<C_zip_code> findAllWhereC_locationIsNull();

    /**
     *  Get the "id" c_zip_code.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_zip_code findOne(Long id);

    /**
     *  Delete the "id" c_zip_code.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
