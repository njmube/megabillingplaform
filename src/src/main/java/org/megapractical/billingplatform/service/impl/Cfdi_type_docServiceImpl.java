package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Cfdi_type_docService;
import org.megapractical.billingplatform.domain.Cfdi_type_doc;
import org.megapractical.billingplatform.repository.Cfdi_type_docRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cfdi_type_doc.
 */
@Service
@Transactional
public class Cfdi_type_docServiceImpl implements Cfdi_type_docService{

    private final Logger log = LoggerFactory.getLogger(Cfdi_type_docServiceImpl.class);
    
    @Inject
    private Cfdi_type_docRepository cfdi_type_docRepository;
    
    /**
     * Save a cfdi_type_doc.
     * 
     * @param cfdi_type_doc the entity to save
     * @return the persisted entity
     */
    public Cfdi_type_doc save(Cfdi_type_doc cfdi_type_doc) {
        log.debug("Request to save Cfdi_type_doc : {}", cfdi_type_doc);
        Cfdi_type_doc result = cfdi_type_docRepository.save(cfdi_type_doc);
        return result;
    }

    /**
     *  Get all the cfdi_type_docs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cfdi_type_doc> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdi_type_docs");
        Page<Cfdi_type_doc> result = cfdi_type_docRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cfdi_type_doc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cfdi_type_doc findOne(Long id) {
        log.debug("Request to get Cfdi_type_doc : {}", id);
        Cfdi_type_doc cfdi_type_doc = cfdi_type_docRepository.findOne(id);
        return cfdi_type_doc;
    }

    /**
     *  Delete the  cfdi_type_doc by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi_type_doc : {}", id);
        cfdi_type_docRepository.delete(id);
    }
}
