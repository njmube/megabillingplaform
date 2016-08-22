package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.C_type_seriesService;
import org.megapractical.billingplatform.domain.C_type_series;
import org.megapractical.billingplatform.repository.C_type_seriesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing C_type_series.
 */
@Service
@Transactional
public class C_type_seriesServiceImpl implements C_type_seriesService{

    private final Logger log = LoggerFactory.getLogger(C_type_seriesServiceImpl.class);
    
    @Inject
    private C_type_seriesRepository c_type_seriesRepository;
    
    /**
     * Save a c_type_series.
     * 
     * @param c_type_series the entity to save
     * @return the persisted entity
     */
    public C_type_series save(C_type_series c_type_series) {
        log.debug("Request to save C_type_series : {}", c_type_series);
        C_type_series result = c_type_seriesRepository.save(c_type_series);
        return result;
    }

    /**
     *  Get all the c_type_series.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<C_type_series> findAll(Pageable pageable) {
        log.debug("Request to get all C_type_series");
        Page<C_type_series> result = c_type_seriesRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one c_type_series by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public C_type_series findOne(Long id) {
        log.debug("Request to get C_type_series : {}", id);
        C_type_series c_type_series = c_type_seriesRepository.findOne(id);
        return c_type_series;
    }

    /**
     *  Delete the  c_type_series by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete C_type_series : {}", id);
        c_type_seriesRepository.delete(id);
    }
}
