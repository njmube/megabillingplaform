package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Freecom_tax_typeService;
import org.megapractical.billingplatform.domain.Freecom_tax_type;
import org.megapractical.billingplatform.repository.Freecom_tax_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Freecom_tax_type.
 */
@Service
@Transactional
public class Freecom_tax_typeServiceImpl implements Freecom_tax_typeService{

    private final Logger log = LoggerFactory.getLogger(Freecom_tax_typeServiceImpl.class);
    
    @Inject
    private Freecom_tax_typeRepository freecom_tax_typeRepository;
    
    /**
     * Save a freecom_tax_type.
     * 
     * @param freecom_tax_type the entity to save
     * @return the persisted entity
     */
    public Freecom_tax_type save(Freecom_tax_type freecom_tax_type) {
        log.debug("Request to save Freecom_tax_type : {}", freecom_tax_type);
        Freecom_tax_type result = freecom_tax_typeRepository.save(freecom_tax_type);
        return result;
    }

    /**
     *  Get all the freecom_tax_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Freecom_tax_type> findAll(Pageable pageable) {
        log.debug("Request to get all Freecom_tax_types");
        Page<Freecom_tax_type> result = freecom_tax_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one freecom_tax_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Freecom_tax_type findOne(Long id) {
        log.debug("Request to get Freecom_tax_type : {}", id);
        Freecom_tax_type freecom_tax_type = freecom_tax_typeRepository.findOne(id);
        return freecom_tax_type;
    }

    /**
     *  Delete the  freecom_tax_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Freecom_tax_type : {}", id);
        freecom_tax_typeRepository.delete(id);
    }
}
