package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Customs_info_partService;
import org.megapractical.billingplatform.domain.Customs_info_part;
import org.megapractical.billingplatform.repository.Customs_info_partRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Customs_info_part.
 */
@Service
@Transactional
public class Customs_info_partServiceImpl implements Customs_info_partService{

    private final Logger log = LoggerFactory.getLogger(Customs_info_partServiceImpl.class);
    
    @Inject
    private Customs_info_partRepository customs_info_partRepository;
    
    /**
     * Save a customs_info_part.
     * 
     * @param customs_info_part the entity to save
     * @return the persisted entity
     */
    public Customs_info_part save(Customs_info_part customs_info_part) {
        log.debug("Request to save Customs_info_part : {}", customs_info_part);
        Customs_info_part result = customs_info_partRepository.save(customs_info_part);
        return result;
    }

    /**
     *  Get all the customs_info_parts.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Customs_info_part> findAll(Pageable pageable) {
        log.debug("Request to get all Customs_info_parts");
        Page<Customs_info_part> result = customs_info_partRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one customs_info_part by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Customs_info_part findOne(Long id) {
        log.debug("Request to get Customs_info_part : {}", id);
        Customs_info_part customs_info_part = customs_info_partRepository.findOne(id);
        return customs_info_part;
    }

    /**
     *  Delete the  customs_info_part by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customs_info_part : {}", id);
        customs_info_partRepository.delete(id);
    }
}
