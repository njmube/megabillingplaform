package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Digital_certificate;
import org.megapractical.billingplatform.service.Digital_certificateService;
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
 * REST controller for managing Digital_certificate.
 */
@RestController
@RequestMapping("/api")
public class Digital_certificateResource {

    private final Logger log = LoggerFactory.getLogger(Digital_certificateResource.class);
        
    @Inject
    private Digital_certificateService digital_certificateService;
    
    /**
     * POST  /digital-certificates : Create a new digital_certificate.
     *
     * @param digital_certificate the digital_certificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new digital_certificate, or with status 400 (Bad Request) if the digital_certificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/digital-certificates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Digital_certificate> createDigital_certificate(@RequestBody Digital_certificate digital_certificate) throws URISyntaxException {
        log.debug("REST request to save Digital_certificate : {}", digital_certificate);
        if (digital_certificate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("digital_certificate", "idexists", "A new digital_certificate cannot already have an ID")).body(null);
        }
        Digital_certificate result = digital_certificateService.save(digital_certificate);
        return ResponseEntity.created(new URI("/api/digital-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("digital_certificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /digital-certificates : Updates an existing digital_certificate.
     *
     * @param digital_certificate the digital_certificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated digital_certificate,
     * or with status 400 (Bad Request) if the digital_certificate is not valid,
     * or with status 500 (Internal Server Error) if the digital_certificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/digital-certificates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Digital_certificate> updateDigital_certificate(@RequestBody Digital_certificate digital_certificate) throws URISyntaxException {
        log.debug("REST request to update Digital_certificate : {}", digital_certificate);
        if (digital_certificate.getId() == null) {
            return createDigital_certificate(digital_certificate);
        }
        Digital_certificate result = digital_certificateService.save(digital_certificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("digital_certificate", digital_certificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /digital-certificates : get all the digital_certificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of digital_certificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/digital-certificates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Digital_certificate>> getAllDigital_certificates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Digital_certificates");
        Page<Digital_certificate> page = digital_certificateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/digital-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /digital-certificates/:id : get the "id" digital_certificate.
     *
     * @param id the id of the digital_certificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the digital_certificate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/digital-certificates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Digital_certificate> getDigital_certificate(@PathVariable Long id) {
        log.debug("REST request to get Digital_certificate : {}", id);
        Digital_certificate digital_certificate = digital_certificateService.findOne(id);
        return Optional.ofNullable(digital_certificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /digital-certificates/:id : delete the "id" digital_certificate.
     *
     * @param id the id of the digital_certificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/digital-certificates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDigital_certificate(@PathVariable Long id) {
        log.debug("REST request to delete Digital_certificate : {}", id);
        digital_certificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("digital_certificate", id.toString())).build();
    }

}
