package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.C_features_work_piece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing C_features_work_piece.
 */
public interface C_features_work_pieceService {

    /**
     * Save a c_features_work_piece.
     * 
     * @param c_features_work_piece the entity to save
     * @return the persisted entity
     */
    C_features_work_piece save(C_features_work_piece c_features_work_piece);

    /**
     *  Get all the c_features_work_pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<C_features_work_piece> findAll(Pageable pageable);

    /**
     *  Get the "id" c_features_work_piece.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    C_features_work_piece findOne(Long id);

    /**
     *  Delete the "id" c_features_work_piece.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
