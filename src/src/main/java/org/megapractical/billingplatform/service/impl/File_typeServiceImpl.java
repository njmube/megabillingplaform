package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.File_typeService;
import org.megapractical.billingplatform.domain.File_type;
import org.megapractical.billingplatform.repository.File_typeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing File_type.
 */
@Service
@Transactional
public class File_typeServiceImpl implements File_typeService{

    private final Logger log = LoggerFactory.getLogger(File_typeServiceImpl.class);

    @Inject
    private File_typeRepository file_typeRepository;

    /**
     * Save a file_type.
     *
     * @param file_type the entity to save
     * @return the persisted entity
     */
    public File_type save(File_type file_type) {
        log.debug("Request to save File_type : {}", file_type);
        File_type result = file_typeRepository.save(file_type);
        return result;
    }

    /**
     *  Get all the file_types.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<File_type> findAll(Pageable pageable) {
        log.debug("Request to get all File_types");
        Page<File_type> result = file_typeRepository.findAll(pageable);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<File_type> findAllByName(String filtername, Pageable pageable) {
        log.debug("Request to get Request_states whith filtername: {}",filtername);
        Page<File_type> result = file_typeRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    /**
     *  Get one file_type by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public File_type findOne(Long id) {
        log.debug("Request to get File_type : {}", id);
        File_type file_type = file_typeRepository.findOne(id);
        return file_type;
    }

    /**
     *  Delete the  file_type by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete File_type : {}", id);
        file_typeRepository.delete(id);
    }
}
