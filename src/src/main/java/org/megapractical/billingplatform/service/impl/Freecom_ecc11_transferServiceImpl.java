package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_ecc11_transferService;
import org.megapractical.billingplatform.domain.Freecom_ecc11_transfer;
import org.megapractical.billingplatform.repository.Freecom_ecc11_transferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_ecc11_transfer.
 */
@Service
@Transactional
public class Freecom_ecc11_transferServiceImpl implements Freecom_ecc11_transferService{

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11_transferServiceImpl.class);
    
    @Inject
    private Freecom_ecc11_transferRepository freecom_ecc11_transferRepository;
    
    /**
     * Save a freecom_ecc11_transfer.
     * 
     * @param freecom_ecc11_transfer the entity to save
     * @return the persisted entity
     */
    public Freecom_ecc11_transfer save(Freecom_ecc11_transfer freecom_ecc11_transfer) {
        log.debug("Request to save Freecom_ecc11_transfer : {}", freecom_ecc11_transfer);
        Freecom_ecc11_transfer result = freecom_ecc11_transferRepository.save(freecom_ecc11_transfer);
        return result;
    }

    /**
     *  Get all the freecom_ecc11_transfers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_ecc11_transfer> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_ecc11_transfers");
        Page<Freecom_ecc11_transfer> result = freecom_ecc11_transferRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_ecc11_transfer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_ecc11_transfer findOne(Long id) {
        log.debug("Request to get Freecom_ecc11_transfer : {}", id);
        Freecom_ecc11_transfer freecom_ecc11_transfer = freecom_ecc11_transferRepository.findOne(id);
        return freecom_ecc11_transfer;
    }

    /**
     *  Delete the  freecom_ecc11_transfer by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_ecc11_transfer : {}", id);
        freecom_ecc11_transferRepository.delete(id);
    }
}
