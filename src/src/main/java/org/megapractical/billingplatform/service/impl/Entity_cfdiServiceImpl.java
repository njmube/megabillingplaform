package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Entity_cfdiService;
import org.megapractical.billingplatform.domain.Entity_cfdi;
import org.megapractical.billingplatform.repository.Entity_cfdiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Entity_cfdi.
 */
@Service
@Transactional
public class Entity_cfdiServiceImpl implements Entity_cfdiService{

    private final Logger log = LoggerFactory.getLogger(Entity_cfdiServiceImpl.class);
    
    @Inject
    private Entity_cfdiRepository entity_cfdiRepository;
    
    /**
     * Save a entity_cfdi.
     * 
     * @param entity_cfdi the entity to save
     * @return the persisted entity
     */
    public Entity_cfdi save(Entity_cfdi entity_cfdi) {
        log.debug("Request to save Entity_cfdi : {}", entity_cfdi);
        Entity_cfdi result = entity_cfdiRepository.save(entity_cfdi);
        return result;
    }

    /**
     *  Get all the entity_cfdis.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Entity_cfdi> findAll(Pageable pageable) {
        log.debug("Request to get all Entity_cfdis");
        Page<Entity_cfdi> result = entity_cfdiRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one entity_cfdi by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Entity_cfdi findOne(Long id) {
        log.debug("Request to get Entity_cfdi : {}", id);
        Entity_cfdi entity_cfdi = entity_cfdiRepository.findOne(id);
        return entity_cfdi;
    }

    /**
     *  Delete the  entity_cfdi by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entity_cfdi : {}", id);
        entity_cfdiRepository.delete(id);
    }
}
