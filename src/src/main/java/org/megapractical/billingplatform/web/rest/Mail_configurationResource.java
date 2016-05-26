package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Mail_configuration;
import org.megapractical.billingplatform.service.Mail_configurationService;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Mail_configuration.
 */
@RestController
@RequestMapping("/api")
public class Mail_configurationResource {

    private final Logger log = LoggerFactory.getLogger(Mail_configurationResource.class);
        
    @Inject
    private Mail_configurationService mail_configurationService;
    
    /**
     * POST  /mail-configurations : Create a new mail_configuration.
     *
     * @param mail_configuration the mail_configuration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mail_configuration, or with status 400 (Bad Request) if the mail_configuration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mail-configurations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mail_configuration> createMail_configuration(@RequestBody Mail_configuration mail_configuration) throws URISyntaxException {
        log.debug("REST request to save Mail_configuration : {}", mail_configuration);
        if (mail_configuration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mail_configuration", "idexists", "A new mail_configuration cannot already have an ID")).body(null);
        }
        Mail_configuration result = mail_configurationService.save(mail_configuration);
        return ResponseEntity.created(new URI("/api/mail-configurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mail_configuration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mail-configurations : Updates an existing mail_configuration.
     *
     * @param mail_configuration the mail_configuration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mail_configuration,
     * or with status 400 (Bad Request) if the mail_configuration is not valid,
     * or with status 500 (Internal Server Error) if the mail_configuration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/mail-configurations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mail_configuration> updateMail_configuration(@RequestBody Mail_configuration mail_configuration) throws URISyntaxException {
        log.debug("REST request to update Mail_configuration : {}", mail_configuration);
        if (mail_configuration.getId() == null) {
            return createMail_configuration(mail_configuration);
        }
        Mail_configuration result = mail_configurationService.save(mail_configuration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mail_configuration", mail_configuration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mail-configurations : get all the mail_configurations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mail_configurations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/mail-configurations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Mail_configuration>> getAllMail_configurations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Mail_configurations");
        Page<Mail_configuration> page = mail_configurationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mail-configurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mail-configurations/:id : get the "id" mail_configuration.
     *
     * @param id the id of the mail_configuration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mail_configuration, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/mail-configurations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mail_configuration> getMail_configuration(@PathVariable Long id) {
        log.debug("REST request to get Mail_configuration : {}", id);
        Mail_configuration mail_configuration = mail_configurationService.findOne(id);
        return Optional.ofNullable(mail_configuration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mail-configurations/:id : delete the "id" mail_configuration.
     *
     * @param id the id of the mail_configuration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/mail-configurations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMail_configuration(@PathVariable Long id) {
        log.debug("REST request to delete Mail_configuration : {}", id);
        mail_configurationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mail_configuration", id.toString())).build();
    }

}
