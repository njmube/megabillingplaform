package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Well_typeService;
import org.megapractical.billingplatform.domain.Well_type;
import org.megapractical.billingplatform.repository.Well_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Well_type.
 */
@Service
@Transactional
public class Well_typeServiceImpl implements Well_typeService{

    private final Logger log = LoggerFactory.getLogger(Well_typeServiceImpl.class);
    
    @Inject
    private Well_typeRepository well_typeRepository;
    
    /**
     * Save a well_type.
     * 
     * @param well_type the entity to save
     * @return the persisted entity
     */
    public Well_type save(Well_type well_type) {
        log.debug("Request to save Well_type : {}", well_type);
        Well_type result = well_typeRepository.save(well_type);
        return result;
    }

    /**
     *  Get all the well_types.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Well_type> findAll(Pageable pageable) {
        log.debug("Request to get all Well_types");
        Page<Well_type> result = well_typeRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one well_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Well_type findOne(Long id) {
        log.debug("Request to get Well_type : {}", id);
        Well_type well_type = well_typeRepository.findOne(id);
        return well_type;
    }

    /**
     *  Delete the  well_type by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Well_type : {}", id);
        well_typeRepository.delete(id);
    }
}
