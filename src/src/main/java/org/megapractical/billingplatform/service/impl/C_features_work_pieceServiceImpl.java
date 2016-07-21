package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_features_work_pieceService;
import org.megapractical.billingplatform.domain.C_features_work_piece;
import org.megapractical.billingplatform.repository.C_features_work_pieceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_features_work_piece.
 */
@Service
@Transactional
public class C_features_work_pieceServiceImpl implements C_features_work_pieceService{

    private final Logger log = LoggerFactory.getLogger(C_features_work_pieceServiceImpl.class);
    
    @Inject
    private C_features_work_pieceRepository c_features_work_pieceRepository;
    
    /**
     * Save a c_features_work_piece.
     * 
     * @param c_features_work_piece the entity to save
     * @return the persisted entity
     */
    public C_features_work_piece save(C_features_work_piece c_features_work_piece) {
        log.debug("Request to save C_features_work_piece : {}", c_features_work_piece);
        C_features_work_piece result = c_features_work_pieceRepository.save(c_features_work_piece);
        return result;
    }

    /**
     *  Get all the c_features_work_pieces.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_features_work_piece> findAll(Pageable pageable) {
        log.debug("Request to get all C_features_work_pieces");
        Page<C_features_work_piece> result = c_features_work_pieceRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_features_work_piece by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_features_work_piece findOne(Long id) {
        log.debug("Request to get C_features_work_piece : {}", id);
        C_features_work_piece c_features_work_piece = c_features_work_pieceRepository.findOne(id);
        return c_features_work_piece;
    }

    /**
     *  Delete the  c_features_work_piece by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_features_work_piece : {}", id);
        c_features_work_pieceRepository.delete(id);
    }
}
