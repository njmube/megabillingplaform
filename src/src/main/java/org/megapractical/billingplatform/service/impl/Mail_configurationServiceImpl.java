package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Mail_configurationService;
import org.megapractical.billingplatform.domain.Mail_configuration;
import org.megapractical.billingplatform.repository.Mail_configurationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import org.xml.sax.helpers.XMLReaderAdapter;

import javax.inject.Inject;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import java.util.List;

/**
 * Service Implementation for managing Mail_configuration.
 */
@Service
@Transactional
public class Mail_configurationServiceImpl implements Mail_configurationService{

    private final Logger log = LoggerFactory.getLogger(Mail_configurationServiceImpl.class);

    @Inject
    private Mail_configurationRepository mail_configurationRepository;

    /**
     * Save a mail_configuration.
     *
     * @param mail_configuration the entity to save
     * @return the persisted entity
     */
    public Mail_configuration save(Mail_configuration mail_configuration) {
        log.debug("Request to save Mail_configuration : {}", mail_configuration);

        Mail_configuration result = mail_configurationRepository.save(mail_configuration);
        return result;
    }

    /**
     *  Get all the mail_configurations.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Mail_configuration> findAll(Pageable pageable) {
        log.debug("Request to get all Mail_configurations");
        Page<Mail_configuration> result = mail_configurationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one mail_configuration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Mail_configuration findOne(Long id) {
        log.debug("Request to get Mail_configuration : {}", id);
        Mail_configuration mail_configuration = mail_configurationRepository.findOne(id);
        return mail_configuration;
    }

    /**
     *  Delete the  mail_configuration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Mail_configuration : {}", id);
        mail_configurationRepository.delete(id);
    }
}
