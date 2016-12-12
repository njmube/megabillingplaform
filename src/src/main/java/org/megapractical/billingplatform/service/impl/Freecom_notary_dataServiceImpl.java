package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_notary_dataService;
import org.megapractical.billingplatform.domain.Freecom_notary_data;
import org.megapractical.billingplatform.repository.Freecom_notary_dataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_notary_data.
 */
@Service
@Transactional
public class Freecom_notary_dataServiceImpl implements Freecom_notary_dataService{

    private final Logger log = LoggerFactory.getLogger(Freecom_notary_dataServiceImpl.class);
    
    @Inject
    private Freecom_notary_dataRepository freecom_notary_dataRepository;
    
    /**
     * Save a freecom_notary_data.
     * 
     * @param freecom_notary_data the entity to save
     * @return the persisted entity
     */
    public Freecom_notary_data save(Freecom_notary_data freecom_notary_data) {
        log.debug("Request to save Freecom_notary_data : {}", freecom_notary_data);
        Freecom_notary_data result = freecom_notary_dataRepository.save(freecom_notary_data);
        return result;
    }

    /**
     *  Get all the freecom_notary_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_notary_data> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_notary_data");
        Page<Freecom_notary_data> result = freecom_notary_dataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_notary_data by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_notary_data findOne(Long id) {
        log.debug("Request to get Freecom_notary_data : {}", id);
        Freecom_notary_data freecom_notary_data = freecom_notary_dataRepository.findOne(id);
        return freecom_notary_data;
    }

    /**
     *  Delete the  freecom_notary_data by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_notary_data : {}", id);
        freecom_notary_dataRepository.delete(id);
    }
}
