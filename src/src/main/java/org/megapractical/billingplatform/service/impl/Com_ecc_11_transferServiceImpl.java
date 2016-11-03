package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Com_ecc_11_transferService;
import org.megapractical.billingplatform.domain.Com_ecc_11_transfer;
import org.megapractical.billingplatform.repository.Com_ecc_11_transferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Com_ecc_11_transfer.
 */
@Service
@Transactional
public class Com_ecc_11_transferServiceImpl implements Com_ecc_11_transferService{

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11_transferServiceImpl.class);
    
    @Inject
    private Com_ecc_11_transferRepository com_ecc_11_transferRepository;
    
    /**
     * Save a com_ecc_11_transfer.
     * 
     * @param com_ecc_11_transfer the entity to save
     * @return the persisted entity
     */
    public Com_ecc_11_transfer save(Com_ecc_11_transfer com_ecc_11_transfer) {
        log.debug("Request to save Com_ecc_11_transfer : {}", com_ecc_11_transfer);
        Com_ecc_11_transfer result = com_ecc_11_transferRepository.save(com_ecc_11_transfer);
        return result;
    }

    /**
     *  Get all the com_ecc_11_transfers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Com_ecc_11_transfer> findAll(Pageable pageable) {
        log.debug("Request to get all Com_ecc_11_transfers");
        Page<Com_ecc_11_transfer> result = com_ecc_11_transferRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one com_ecc_11_transfer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Com_ecc_11_transfer findOne(Long id) {
        log.debug("Request to get Com_ecc_11_transfer : {}", id);
        Com_ecc_11_transfer com_ecc_11_transfer = com_ecc_11_transferRepository.findOne(id);
        return com_ecc_11_transfer;
    }

    /**
     *  Delete the  com_ecc_11_transfer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Com_ecc_11_transfer : {}", id);
        com_ecc_11_transferRepository.delete(id);
    }
}
