package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Config_pathrootfileService;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.repository.Config_pathrootfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Config_pathrootfile.
 */
@Service
@Transactional
public class Config_pathrootfileServiceImpl implements Config_pathrootfileService{

    private final Logger log = LoggerFactory.getLogger(Config_pathrootfileServiceImpl.class);
    
    @Inject
    private Config_pathrootfileRepository config_pathrootfileRepository;
    
    /**
     * Save a config_pathrootfile.
     * 
     * @param config_pathrootfile the entity to save
     * @return the persisted entity
     */
    public Config_pathrootfile save(Config_pathrootfile config_pathrootfile) {
        log.debug("Request to save Config_pathrootfile : {}", config_pathrootfile);
        Config_pathrootfile result = config_pathrootfileRepository.save(config_pathrootfile);
        return result;
    }

    /**
     *  Get all the config_pathrootfiles.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Config_pathrootfile> findAll(Pageable pageable) {
        log.debug("Request to get all Config_pathrootfiles");
        Page<Config_pathrootfile> result = config_pathrootfileRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one config_pathrootfile by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Config_pathrootfile findOne(Long id) {
        log.debug("Request to get Config_pathrootfile : {}", id);
        Config_pathrootfile config_pathrootfile = config_pathrootfileRepository.findOne(id);
        return config_pathrootfile;
    }

    /**
     *  Delete the  config_pathrootfile by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Config_pathrootfile : {}", id);
        config_pathrootfileRepository.delete(id);
    }
}
