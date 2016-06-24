package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_digital_certificate;
import org.megapractical.billingplatform.service.Free_digital_certificateService;
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
 * REST controller for managing Free_digital_certificate.
 */
@RestController
@RequestMapping("/api")
public class Free_digital_certificateResource {

    private final Logger log = LoggerFactory.getLogger(Free_digital_certificateResource.class);
        
    @Inject
    private Free_digital_certificateService free_digital_certificateService;
    
    /**
     * POST  /free-digital-certificates : Create a new free_digital_certificate.
     *
     * @param free_digital_certificate the free_digital_certificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_digital_certificate, or with status 400 (Bad Request) if the free_digital_certificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-digital-certificates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_digital_certificate> createFree_digital_certificate(@Valid @RequestBody Free_digital_certificate free_digital_certificate) throws URISyntaxException {
        log.debug("REST request to save Free_digital_certificate : {}", free_digital_certificate);
        if (free_digital_certificate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_digital_certificate", "idexists", "A new free_digital_certificate cannot already have an ID")).body(null);
        }
        Free_digital_certificate result = free_digital_certificateService.save(free_digital_certificate);
        return ResponseEntity.created(new URI("/api/free-digital-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_digital_certificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-digital-certificates : Updates an existing free_digital_certificate.
     *
     * @param free_digital_certificate the free_digital_certificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_digital_certificate,
     * or with status 400 (Bad Request) if the free_digital_certificate is not valid,
     * or with status 500 (Internal Server Error) if the free_digital_certificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-digital-certificates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_digital_certificate> updateFree_digital_certificate(@Valid @RequestBody Free_digital_certificate free_digital_certificate) throws URISyntaxException {
        log.debug("REST request to update Free_digital_certificate : {}", free_digital_certificate);
        if (free_digital_certificate.getId() == null) {
            return createFree_digital_certificate(free_digital_certificate);
        }
        Free_digital_certificate result = free_digital_certificateService.save(free_digital_certificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_digital_certificate", free_digital_certificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-digital-certificates : get all the free_digital_certificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_digital_certificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-digital-certificates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_digital_certificate>> getAllFree_digital_certificates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_digital_certificates");
        Page<Free_digital_certificate> page = free_digital_certificateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-digital-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-digital-certificates/:id : get the "id" free_digital_certificate.
     *
     * @param id the id of the free_digital_certificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_digital_certificate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-digital-certificates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_digital_certificate> getFree_digital_certificate(@PathVariable Long id) {
        log.debug("REST request to get Free_digital_certificate : {}", id);
        Free_digital_certificate free_digital_certificate = free_digital_certificateService.findOne(id);
        return Optional.ofNullable(free_digital_certificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-digital-certificates/:id : delete the "id" free_digital_certificate.
     *
     * @param id the id of the free_digital_certificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-digital-certificates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_digital_certificate(@PathVariable Long id) {
        log.debug("REST request to delete Free_digital_certificate : {}", id);
        free_digital_certificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_digital_certificate", id.toString())).build();
    }

}
