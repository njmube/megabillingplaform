package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_notary_dataService;
import org.megapractical.billingplatform.domain.Com_notary_data;
import org.megapractical.billingplatform.repository.Com_notary_dataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_notary_data.
 */
@Service
@Transactional
public class Com_notary_dataServiceImpl implements Com_notary_dataService{

    private final Logger log = LoggerFactory.getLogger(Com_notary_dataServiceImpl.class);
    
    @Inject
    private Com_notary_dataRepository com_notary_dataRepository;
    
    /**
     * Save a com_notary_data.
     * 
     * @param com_notary_data the entity to save
     * @return the persisted entity
     */
    public Com_notary_data save(Com_notary_data com_notary_data) {
        log.debug("Request to save Com_notary_data : {}", com_notary_data);
        Com_notary_data result = com_notary_dataRepository.save(com_notary_data);
        return result;
    }

    /**
     *  Get all the com_notary_data.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_notary_data> findAll(Pageable pageable) {
        log.debug("Request to get all Com_notary_data");
        Page<Com_notary_data> result = com_notary_dataRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_notary_data by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_notary_data findOne(Long id) {
        log.debug("Request to get Com_notary_data : {}", id);
        Com_notary_data com_notary_data = com_notary_dataRepository.findOne(id);
        return com_notary_data;
    }

    /**
     *  Delete the  com_notary_data by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_notary_data : {}", id);
        com_notary_dataRepository.delete(id);
    }
}
