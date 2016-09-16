package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.CfdiService;
import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.repository.CfdiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Cfdi.
 */
@Service
@Transactional
public class CfdiServiceImpl implements CfdiService{

    private final Logger log = LoggerFactory.getLogger(CfdiServiceImpl.class);
    
    @Inject
    private CfdiRepository cfdiRepository;
    
    /**
     * Save a cfdi.
     * 
     * @param cfdi the entity to save
     * @return the persisted entity
     */
    public Cfdi save(Cfdi cfdi) {
        log.debug("Request to save Cfdi : {}", cfdi);
        Cfdi result = cfdiRepository.save(cfdi);
        return result;
    }

    /**
     *  Get all the cfdis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Cfdis");
        Page<Cfdi> result = cfdiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Cfdi findOne(Long id) {
        log.debug("Request to get Cfdi : {}", id);
        Cfdi cfdi = cfdiRepository.findOne(id);
        return cfdi;
    }

    /**
     *  Delete the  cfdi by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cfdi : {}", id);
        cfdiRepository.delete(id);
    }
}
