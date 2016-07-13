package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.LegendService;
import org.megapractical.billingplatform.domain.Legend;
import org.megapractical.billingplatform.repository.LegendRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Legend.
 */
@Service
@Transactional
public class LegendServiceImpl implements LegendService{

    private final Logger log = LoggerFactory.getLogger(LegendServiceImpl.class);
    
    @Inject
    private LegendRepository legendRepository;
    
    /**
     * Save a legend.
     * 
     * @param legend the entity to save
     * @return the persisted entity
     */
    public Legend save(Legend legend) {
        log.debug("Request to save Legend : {}", legend);
        Legend result = legendRepository.save(legend);
        return result;
    }

    /**
     *  Get all the legends.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Legend> findAll(Pageable pageable) {
        log.debug("Request to get all Legends");
        Page<Legend> result = legendRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one legend by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Legend findOne(Long id) {
        log.debug("Request to get Legend : {}", id);
        Legend legend = legendRepository.findOne(id);
        return legend;
    }

    /**
     *  Delete the  legend by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Legend : {}", id);
        legendRepository.delete(id);
    }
}
