package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_dataenajenantecopscService;
import org.megapractical.billingplatform.domain.Com_dataenajenantecopsc;
import org.megapractical.billingplatform.repository.Com_dataenajenantecopscRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_dataenajenantecopsc.
 */
@Service
@Transactional
public class Com_dataenajenantecopscServiceImpl implements Com_dataenajenantecopscService{

    private final Logger log = LoggerFactory.getLogger(Com_dataenajenantecopscServiceImpl.class);
    
    @Inject
    private Com_dataenajenantecopscRepository com_dataenajenantecopscRepository;
    
    /**
     * Save a com_dataenajenantecopsc.
     * 
     * @param com_dataenajenantecopsc the entity to save
     * @return the persisted entity
     */
    public Com_dataenajenantecopsc save(Com_dataenajenantecopsc com_dataenajenantecopsc) {
        log.debug("Request to save Com_dataenajenantecopsc : {}", com_dataenajenantecopsc);
        Com_dataenajenantecopsc result = com_dataenajenantecopscRepository.save(com_dataenajenantecopsc);
        return result;
    }

    /**
     *  Get all the com_dataenajenantecopscs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_dataenajenantecopsc> findAll(Pageable pageable) {
        log.debug("Request to get all Com_dataenajenantecopscs");
        Page<Com_dataenajenantecopsc> result = com_dataenajenantecopscRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_dataenajenantecopsc by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_dataenajenantecopsc findOne(Long id) {
        log.debug("Request to get Com_dataenajenantecopsc : {}", id);
        Com_dataenajenantecopsc com_dataenajenantecopsc = com_dataenajenantecopscRepository.findOne(id);
        return com_dataenajenantecopsc;
    }

    /**
     *  Delete the  com_dataenajenantecopsc by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_dataenajenantecopsc : {}", id);
        com_dataenajenantecopscRepository.delete(id);
    }
}
