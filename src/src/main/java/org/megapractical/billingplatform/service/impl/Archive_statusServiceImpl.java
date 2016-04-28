package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Archive_statusService;
import org.megapractical.billingplatform.domain.Archive_status;
import org.megapractical.billingplatform.repository.Archive_statusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Archive_status.
 */
@Service
@Transactional
public class Archive_statusServiceImpl implements Archive_statusService{

    private final Logger log = LoggerFactory.getLogger(Archive_statusServiceImpl.class);
    
    @Inject
    private Archive_statusRepository archive_statusRepository;
    
    /**
     * Save a archive_status.
     * 
     * @param archive_status the entity to save
     * @return the persisted entity
     */
    public Archive_status save(Archive_status archive_status) {
        log.debug("Request to save Archive_status : {}", archive_status);
        Archive_status result = archive_statusRepository.save(archive_status);
        return result;
    }

    /**
     *  Get all the archive_statuses.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Archive_status> findAll(Pageable pageable) {
        log.debug("Request to get all Archive_statuses");
        Page<Archive_status> result = archive_statusRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one archive_status by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Archive_status findOne(Long id) {
        log.debug("Request to get Archive_status : {}", id);
        Archive_status archive_status = archive_statusRepository.findOne(id);
        return archive_status;
    }

    /**
     *  Delete the  archive_status by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Archive_status : {}", id);
        archive_statusRepository.delete(id);
    }
}
