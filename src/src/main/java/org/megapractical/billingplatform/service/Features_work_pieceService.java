package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Features_work_piece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Features_work_piece.
 */
public interface Features_work_pieceService {

    /**
     * Save a features_work_piece.
     * 
     * @param features_work_piece the entity to save
     * @return the persisted entity
     */
    Features_work_piece save(Features_work_piece features_work_piece);

    /**
     *  Get all the features_work_pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Features_work_piece> findAll(Pageable pageable);

    /**
     *  Get the "id" features_work_piece.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Features_work_piece findOne(Long id);

    /**
     *  Delete the "id" features_work_piece.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
