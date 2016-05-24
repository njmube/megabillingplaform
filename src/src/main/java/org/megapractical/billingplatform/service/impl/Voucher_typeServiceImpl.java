package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Voucher_typeService;
import org.megapractical.billingplatform.domain.Voucher_type;
import org.megapractical.billingplatform.repository.Voucher_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Voucher_type.
 */
@Service
@Transactional
public class Voucher_typeServiceImpl implements Voucher_typeService{

    private final Logger log = LoggerFactory.getLogger(Voucher_typeServiceImpl.class);
    
    @Inject
    private Voucher_typeRepository voucher_typeRepository;
    
    /**
     * Save a voucher_type.
     * 
     * @param voucher_type the entity to save
     * @return the persisted entity
     */
    public Voucher_type save(Voucher_type voucher_type) {
        log.debug("Request to save Voucher_type : {}", voucher_type);
        Voucher_type result = voucher_typeRepository.save(voucher_type);
        return result;
    }

    /**
     *  Get all the voucher_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Voucher_type> findAll(Pageable pageable) {
        log.debug("Request to get all Voucher_types");
        Page<Voucher_type> result = voucher_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one voucher_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Voucher_type findOne(Long id) {
        log.debug("Request to get Voucher_type : {}", id);
        Voucher_type voucher_type = voucher_typeRepository.findOne(id);
        return voucher_type;
    }

    /**
     *  Delete the  voucher_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Voucher_type : {}", id);
        voucher_typeRepository.delete(id);
    }
}
