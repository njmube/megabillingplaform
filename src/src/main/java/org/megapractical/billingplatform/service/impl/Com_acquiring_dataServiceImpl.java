package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_acquiring_dataService;
import org.megapractical.billingplatform.domain.Com_acquiring_data;
import org.megapractical.billingplatform.repository.Com_acquiring_dataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_acquiring_data.
 */
@Service
@Transactional
public class Com_acquiring_dataServiceImpl implements Com_acquiring_dataService{

    private final Logger log = LoggerFactory.getLogger(Com_acquiring_dataServiceImpl.class);
    
    @Inject
    private Com_acquiring_dataRepository com_acquiring_dataRepository;
    
    /**
     * Save a com_acquiring_data.
     * 
     * @param com_acquiring_data the entity to save
     * @return the persisted entity
     */
    public Com_acquiring_data save(Com_acquiring_data com_acquiring_data) {
        log.debug("Request to save Com_acquiring_data : {}", com_acquiring_data);
        Com_acquiring_data result = com_acquiring_dataRepository.save(com_acquiring_data);
        return result;
    }

    /**
     *  Get all the com_acquiring_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_acquiring_data> findAll(Pageable pageable) {
        log.debug("Request to get all Com_acquiring_data");
        Page<Com_acquiring_data> result = com_acquiring_dataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_acquiring_data by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_acquiring_data findOne(Long id) {
        log.debug("Request to get Com_acquiring_data : {}", id);
        Com_acquiring_data com_acquiring_data = com_acquiring_dataRepository.findOne(id);
        return com_acquiring_data;
    }

    /**
     *  Delete the  com_acquiring_data by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_acquiring_data : {}", id);
        com_acquiring_dataRepository.delete(id);
    }
}
