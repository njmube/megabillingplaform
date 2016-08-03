package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.service.C_features_work_pieceService;
import org.megapractical.billingplatform.domain.C_features_work_piece;
import org.megapractical.billingplatform.repository.C_features_work_pieceRepository;
import org.megapractical.billingplatform.service.C_state_eventService;
import org.megapractical.billingplatform.service.TracemgService;
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

    @Inject
    private Audit_event_typeService audit_event_typeService;

    @Inject
    private C_state_eventService c_state_eventService;

    @Inject
    private TracemgService tracemgService;

    /**
     * Save a c_features_work_piece.
     *
     * @param c_features_work_piece the entity to save
     * @return the persisted entity
     */
    public C_features_work_piece save(C_features_work_piece c_features_work_piece) {
        log.debug("Request to save C_features_work_piece : {}", c_features_work_piece);
        C_features_work_piece result = c_features_work_pieceRepository.save(c_features_work_piece);
        Long idauditevent = new Long("16");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        if(result != null){
            Long idstate = new Long("1");
            c_state_event = c_state_eventService.findOne(idstate);
        }
        else
        {
            Long idstate = new Long("2");
            c_state_event = c_state_eventService.findOne(idstate);
        }
        tracemgService.saveTrace(audit_event_type, c_state_event);
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
        Long idauditevent = new Long("17");
        Audit_event_type audit_event_type = audit_event_typeService.findOne(idauditevent);
        C_state_event c_state_event;
        Long idstate = new Long("1");
        c_state_event = c_state_eventService.findOne(idstate);
        tracemgService.saveTrace(audit_event_type, c_state_event);
    }
}
