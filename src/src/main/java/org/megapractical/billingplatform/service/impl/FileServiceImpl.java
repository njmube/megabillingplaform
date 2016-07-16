package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.FileService;
import org.megapractical.billingplatform.domain.File;
import org.megapractical.billingplatform.repository.FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing File.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService{

    private final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Inject
    private FileRepository fileRepository;

    /**
     * Save a file.
     *
     * @param file the entity to save
     * @return the persisted entity
     */
    public File save(File file) {
        log.debug("Request to save File : {}", file);
        File result = fileRepository.save(file);
        return result;
    }

    @Transactional(readOnly = true)
    public Page<File> findAllByName(String filtername, Pageable pageable) {
        log.debug("Request to get File whith filtername: {}",filtername);
        Page<File> result = fileRepository.findByNameStartingWith(filtername, pageable);
        return result;
    }

    /**
     *  Get all the files.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<File> findAll(Pageable pageable) {
        log.debug("Request to get all Files");
        Page<File> result = fileRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one file by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public File findOne(Long id) {
        log.debug("Request to get File : {}", id);
        File file = fileRepository.findOne(id);
        return file;
    }

    /**
     *  Delete the  file by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete File : {}", id);
        fileRepository.delete(id);
    }
}
