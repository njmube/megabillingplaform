package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_destruction_certificate;
import org.megapractical.billingplatform.service.Freecom_destruction_certificateService;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Freecom_destruction_certificate.
 */
@RestController
@RequestMapping("/api")
public class Freecom_destruction_certificateResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_destruction_certificateResource.class);
        
    @Inject
    private Freecom_destruction_certificateService freecom_destruction_certificateService;
    
    /**
     * POST  /freecom-destruction-certificates : Create a new freecom_destruction_certificate.
     *
     * @param freecom_destruction_certificate the freecom_destruction_certificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_destruction_certificate, or with status 400 (Bad Request) if the freecom_destruction_certificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-destruction-certificates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_destruction_certificate> createFreecom_destruction_certificate(@Valid @RequestBody Freecom_destruction_certificate freecom_destruction_certificate) throws URISyntaxException {
        log.debug("REST request to save Freecom_destruction_certificate : {}", freecom_destruction_certificate);
        if (freecom_destruction_certificate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_destruction_certificate", "idexists", "A new freecom_destruction_certificate cannot already have an ID")).body(null);
        }
        Freecom_destruction_certificate result = freecom_destruction_certificateService.save(freecom_destruction_certificate);
        return ResponseEntity.created(new URI("/api/freecom-destruction-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_destruction_certificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-destruction-certificates : Updates an existing freecom_destruction_certificate.
     *
     * @param freecom_destruction_certificate the freecom_destruction_certificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_destruction_certificate,
     * or with status 400 (Bad Request) if the freecom_destruction_certificate is not valid,
     * or with status 500 (Internal Server Error) if the freecom_destruction_certificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-destruction-certificates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_destruction_certificate> updateFreecom_destruction_certificate(@Valid @RequestBody Freecom_destruction_certificate freecom_destruction_certificate) throws URISyntaxException {
        log.debug("REST request to update Freecom_destruction_certificate : {}", freecom_destruction_certificate);
        if (freecom_destruction_certificate.getId() == null) {
            return createFreecom_destruction_certificate(freecom_destruction_certificate);
        }
        Freecom_destruction_certificate result = freecom_destruction_certificateService.save(freecom_destruction_certificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_destruction_certificate", freecom_destruction_certificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-destruction-certificates : get all the freecom_destruction_certificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_destruction_certificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-destruction-certificates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_destruction_certificate>> getAllFreecom_destruction_certificates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_destruction_certificates");
        Page<Freecom_destruction_certificate> page = freecom_destruction_certificateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-destruction-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-destruction-certificates/:id : get the "id" freecom_destruction_certificate.
     *
     * @param id the id of the freecom_destruction_certificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_destruction_certificate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-destruction-certificates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_destruction_certificate> getFreecom_destruction_certificate(@PathVariable Long id) {
        log.debug("REST request to get Freecom_destruction_certificate : {}", id);
        Freecom_destruction_certificate freecom_destruction_certificate = freecom_destruction_certificateService.findOne(id);
        return Optional.ofNullable(freecom_destruction_certificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-destruction-certificates/:id : delete the "id" freecom_destruction_certificate.
     *
     * @param id the id of the freecom_destruction_certificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-destruction-certificates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_destruction_certificate(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_destruction_certificate : {}", id);
        freecom_destruction_certificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_destruction_certificate", id.toString())).build();
    }

}
