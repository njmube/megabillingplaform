package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Cfdi_typesService;
import org.megapractical.billingplatform.domain.Cfdi_types;
import org.megapractical.billingplatform.repository.Cfdi_typesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cfdi_types.
 */
@Service
@Transactional
public class Cfdi_typesServiceImpl implements Cfdi_typesService{

    private final Logger log = LoggerFactory.getLogger(Cfdi_typesServiceImpl.class);
    
    @Inject
    private Cfdi_typesRepository cfdi_typesRepository;
    
    /**
     * Save a cfdi_types.
     * 
     * @param cfdi_types the entity to save
     * @return the persisted entity
     */
    public Cfdi_types save(Cfdi_types cfdi_types) {
        log.debug("Request to save Cfdi_types : {}", cfdi_types);
        Cfdi_types result = cfdi_typesRepository.save(cfdi_types);
        return result;
    }

    /**
     *  Get all the cfdi_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cfdi_types> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdi_types");
        Page<Cfdi_types> result = cfdi_typesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cfdi_types by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cfdi_types findOne(Long id) {
        log.debug("Request to get Cfdi_types : {}", id);
        Cfdi_types cfdi_types = cfdi_typesRepository.findOne(id);
        return cfdi_types;
    }

    /**
     *  Delete the  cfdi_types by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi_types : {}", id);
        cfdi_typesRepository.delete(id);
    }
}
