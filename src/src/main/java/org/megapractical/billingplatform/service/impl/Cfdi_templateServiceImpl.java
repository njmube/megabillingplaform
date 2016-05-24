package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Cfdi_templateService;
import org.megapractical.billingplatform.domain.Cfdi_template;
import org.megapractical.billingplatform.repository.Cfdi_templateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cfdi_template.
 */
@Service
@Transactional
public class Cfdi_templateServiceImpl implements Cfdi_templateService{

    private final Logger log = LoggerFactory.getLogger(Cfdi_templateServiceImpl.class);
    
    @Inject
    private Cfdi_templateRepository cfdi_templateRepository;
    
    /**
     * Save a cfdi_template.
     * 
     * @param cfdi_template the entity to save
     * @return the persisted entity
     */
    public Cfdi_template save(Cfdi_template cfdi_template) {
        log.debug("Request to save Cfdi_template : {}", cfdi_template);
        Cfdi_template result = cfdi_templateRepository.save(cfdi_template);
        return result;
    }

    /**
     *  Get all the cfdi_templates.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cfdi_template> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdi_templates");
        Page<Cfdi_template> result = cfdi_templateRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cfdi_template by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cfdi_template findOne(Long id) {
        log.debug("Request to get Cfdi_template : {}", id);
        Cfdi_template cfdi_template = cfdi_templateRepository.findOne(id);
        return cfdi_template;
    }

    /**
     *  Delete the  cfdi_template by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi_template : {}", id);
        cfdi_templateRepository.delete(id);
    }
}
