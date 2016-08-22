package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_legendService;
import org.megapractical.billingplatform.domain.Freecom_legend;
import org.megapractical.billingplatform.repository.Freecom_legendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_legend.
 */
@Service
@Transactional
public class Freecom_legendServiceImpl implements Freecom_legendService{

    private final Logger log = LoggerFactory.getLogger(Freecom_legendServiceImpl.class);
    
    @Inject
    private Freecom_legendRepository freecom_legendRepository;
    
    /**
     * Save a freecom_legend.
     * 
     * @param freecom_legend the entity to save
     * @return the persisted entity
     */
    public Freecom_legend save(Freecom_legend freecom_legend) {
        log.debug("Request to save Freecom_legend : {}", freecom_legend);
        Freecom_legend result = freecom_legendRepository.save(freecom_legend);
        return result;
    }

    /**
     *  Get all the freecom_legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_legend> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_legends");
        Page<Freecom_legend> result = freecom_legendRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_legend by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_legend findOne(Long id) {
        log.debug("Request to get Freecom_legend : {}", id);
        Freecom_legend freecom_legend = freecom_legendRepository.findOne(id);
        return freecom_legend;
    }

    /**
     *  Delete the  freecom_legend by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_legend : {}", id);
        freecom_legendRepository.delete(id);
    }
}
