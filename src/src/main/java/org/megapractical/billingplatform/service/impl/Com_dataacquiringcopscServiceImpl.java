package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_dataacquiringcopscService;
import org.megapractical.billingplatform.domain.Com_dataacquiringcopsc;
import org.megapractical.billingplatform.repository.Com_dataacquiringcopscRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_dataacquiringcopsc.
 */
@Service
@Transactional
public class Com_dataacquiringcopscServiceImpl implements Com_dataacquiringcopscService{

    private final Logger log = LoggerFactory.getLogger(Com_dataacquiringcopscServiceImpl.class);
    
    @Inject
    private Com_dataacquiringcopscRepository com_dataacquiringcopscRepository;
    
    /**
     * Save a com_dataacquiringcopsc.
     * 
     * @param com_dataacquiringcopsc the entity to save
     * @return the persisted entity
     */
    public Com_dataacquiringcopsc save(Com_dataacquiringcopsc com_dataacquiringcopsc) {
        log.debug("Request to save Com_dataacquiringcopsc : {}", com_dataacquiringcopsc);
        Com_dataacquiringcopsc result = com_dataacquiringcopscRepository.save(com_dataacquiringcopsc);
        return result;
    }

    /**
     *  Get all the com_dataacquiringcopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_dataacquiringcopsc> findAll(Pageable pageable) {
        log.debug("Request to get all Com_dataacquiringcopscs");
        Page<Com_dataacquiringcopsc> result = com_dataacquiringcopscRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_dataacquiringcopsc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_dataacquiringcopsc findOne(Long id) {
        log.debug("Request to get Com_dataacquiringcopsc : {}", id);
        Com_dataacquiringcopsc com_dataacquiringcopsc = com_dataacquiringcopscRepository.findOne(id);
        return com_dataacquiringcopsc;
    }

    /**
     *  Delete the  com_dataacquiringcopsc by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_dataacquiringcopsc : {}", id);
        com_dataacquiringcopscRepository.delete(id);
    }
}
