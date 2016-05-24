package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Voucher_type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Voucher_type.
 */
public interface Voucher_typeService {

    /**
     * Save a voucher_type.
     * 
     * @param voucher_type the entity to save
     * @return the persisted entity
     */
    Voucher_type save(Voucher_type voucher_type);

    /**
     *  Get all the voucher_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Voucher_type> findAll(Pageable pageable);

    /**
     *  Get the "id" voucher_type.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Voucher_type findOne(Long id);

    /**
     *  Delete the "id" voucher_type.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
