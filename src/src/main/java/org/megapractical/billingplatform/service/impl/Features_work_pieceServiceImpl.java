package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Features_work_pieceService;
import org.megapractical.billingplatform.domain.Features_work_piece;
import org.megapractical.billingplatform.repository.Features_work_pieceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Features_work_piece.
 */
@Service
@Transactional
public class Features_work_pieceServiceImpl implements Features_work_pieceService{

    private final Logger log = LoggerFactory.getLogger(Features_work_pieceServiceImpl.class);
    
    @Inject
    private Features_work_pieceRepository features_work_pieceRepository;
    
    /**
     * Save a features_work_piece.
     * 
     * @param features_work_piece the entity to save
     * @return the persisted entity
     */
    public Features_work_piece save(Features_work_piece features_work_piece) {
        log.debug("Request to save Features_work_piece : {}", features_work_piece);
        Features_work_piece result = features_work_pieceRepository.save(features_work_piece);
        return result;
    }

    /**
     *  Get all the features_work_pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Features_work_piece> findAll(Pageable pageable) {
        log.debug("Request to get all Features_work_pieces");
        Page<Features_work_piece> result = features_work_pieceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one features_work_piece by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Features_work_piece findOne(Long id) {
        log.debug("Request to get Features_work_piece : {}", id);
        Features_work_piece features_work_piece = features_work_pieceRepository.findOne(id);
        return features_work_piece;
    }

    /**
     *  Delete the  features_work_piece by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Features_work_piece : {}", id);
        features_work_pieceRepository.delete(id);
    }
}
