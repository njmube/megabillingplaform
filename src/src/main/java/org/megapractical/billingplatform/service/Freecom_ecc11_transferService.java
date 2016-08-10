package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Freecom_ecc11_transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Freecom_ecc11_transfer.
 */
public interface Freecom_ecc11_transferService {

    /**
     * Save a freecom_ecc11_transfer.
     * 
     * @param freecom_ecc11_transfer the entity to save
     * @return the persisted entity
     */
    Freecom_ecc11_transfer save(Freecom_ecc11_transfer freecom_ecc11_transfer);

    /**
     *  Get all the freecom_ecc11_transfers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Freecom_ecc11_transfer> findAll(Pageable pageable);

    /**
     *  Get the "id" freecom_ecc11_transfer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Freecom_ecc11_transfer findOne(Long id);

    /**
     *  Delete the "id" freecom_ecc11_transfer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
