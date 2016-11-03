package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Com_ecc_11_transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Com_ecc_11_transfer.
 */
public interface Com_ecc_11_transferService {

    /**
     * Save a com_ecc_11_transfer.
     * 
     * @param com_ecc_11_transfer the entity to save
     * @return the persisted entity
     */
    Com_ecc_11_transfer save(Com_ecc_11_transfer com_ecc_11_transfer);

    /**
     *  Get all the com_ecc_11_transfers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Com_ecc_11_transfer> findAll(Pageable pageable);

    /**
     *  Get the "id" com_ecc_11_transfer.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Com_ecc_11_transfer findOne(Long id);

    /**
     *  Delete the "id" com_ecc_11_transfer.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
