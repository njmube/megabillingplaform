package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_acquiring_dataService;
import org.megapractical.billingplatform.domain.Freecom_acquiring_data;
import org.megapractical.billingplatform.repository.Freecom_acquiring_dataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_acquiring_data.
 */
@Service
@Transactional
public class Freecom_acquiring_dataServiceImpl implements Freecom_acquiring_dataService{

    private final Logger log = LoggerFactory.getLogger(Freecom_acquiring_dataServiceImpl.class);
    
    @Inject
    private Freecom_acquiring_dataRepository freecom_acquiring_dataRepository;
    
    /**
     * Save a freecom_acquiring_data.
     * 
     * @param freecom_acquiring_data the entity to save
     * @return the persisted entity
     */
    public Freecom_acquiring_data save(Freecom_acquiring_data freecom_acquiring_data) {
        log.debug("Request to save Freecom_acquiring_data : {}", freecom_acquiring_data);
        Freecom_acquiring_data result = freecom_acquiring_dataRepository.save(freecom_acquiring_data);
        return result;
    }

    /**
     *  Get all the freecom_acquiring_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_acquiring_data> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_acquiring_data");
        Page<Freecom_acquiring_data> result = freecom_acquiring_dataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_acquiring_data by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_acquiring_data findOne(Long id) {
        log.debug("Request to get Freecom_acquiring_data : {}", id);
        Freecom_acquiring_data freecom_acquiring_data = freecom_acquiring_dataRepository.findOne(id);
        return freecom_acquiring_data;
    }

    /**
     *  Delete the  freecom_acquiring_data by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_acquiring_data : {}", id);
        freecom_acquiring_dataRepository.delete(id);
    }
}
