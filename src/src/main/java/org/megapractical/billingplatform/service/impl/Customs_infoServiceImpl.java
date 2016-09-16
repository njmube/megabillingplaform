package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Customs_infoService;
import org.megapractical.billingplatform.domain.Customs_info;
import org.megapractical.billingplatform.repository.Customs_infoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Customs_info.
 */
@Service
@Transactional
public class Customs_infoServiceImpl implements Customs_infoService{

    private final Logger log = LoggerFactory.getLogger(Customs_infoServiceImpl.class);
    
    @Inject
    private Customs_infoRepository customs_infoRepository;
    
    /**
     * Save a customs_info.
     * 
     * @param customs_info the entity to save
     * @return the persisted entity
     */
    public Customs_info save(Customs_info customs_info) {
        log.debug("Request to save Customs_info : {}", customs_info);
        Customs_info result = customs_infoRepository.save(customs_info);
        return result;
    }

    /**
     *  Get all the customs_infos.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Customs_info> findAll(Pageable pageable) {
        log.debug("Request to get all Customs_infos");
        Page<Customs_info> result = customs_infoRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one customs_info by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Customs_info findOne(Long id) {
        log.debug("Request to get Customs_info : {}", id);
        Customs_info customs_info = customs_infoRepository.findOne(id);
        return customs_info;
    }

    /**
     *  Delete the  customs_info by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Customs_info : {}", id);
        customs_infoRepository.delete(id);
    }
}
