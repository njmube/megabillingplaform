package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Audit_event_typeService;
import org.megapractical.billingplatform.domain.Audit_event_type;
import org.megapractical.billingplatform.repository.Audit_event_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Audit_event_type.
 */
@Service
@Transactional
public class Audit_event_typeServiceImpl implements Audit_event_typeService{

    private final Logger log = LoggerFactory.getLogger(Audit_event_typeServiceImpl.class);

    @Inject
    private Audit_event_typeRepository audit_event_typeRepository;

    /**
     * Save a audit_event_type.
     *
     * @param audit_event_type the entity to save
     * @return the persisted entity
     */
    public Audit_event_type save(Audit_event_type audit_event_type) {
        log.debug("Request to save Audit_event_type : {}", audit_event_type);
        Audit_event_type result = audit_event_typeRepository.save(audit_event_type);
        return result;
    }

    public Audit_event_type findByName(String name){
        return audit_event_typeRepository.findByName(name);
    }
    /**
     *  Get all the audit_event_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Audit_event_type> findAll(Pageable pageable) {
        log.debug("Request to get all Audit_event_types");
        Page<Audit_event_type> result = audit_event_typeRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Audit_event_type> findAll(){
        return audit_event_typeRepository.findAll();
    }

    /**
     *  Get one audit_event_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Audit_event_type findOne(Long id) {
        log.debug("Request to get Audit_event_type : {}", id);
        Audit_event_type audit_event_type = audit_event_typeRepository.findOne(id);
        return audit_event_type;
    }

    /**
     *  Delete the  audit_event_type by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Audit_event_type : {}", id);
        audit_event_typeRepository.delete(id);
    }
}
