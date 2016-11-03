package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_destruction_certificate;
import org.megapractical.billingplatform.service.Com_destruction_certificateService;
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
 * REST controller for managing Com_destruction_certificate.
 */
@RestController
@RequestMapping("/api")
public class Com_destruction_certificateResource {

    private final Logger log = LoggerFactory.getLogger(Com_destruction_certificateResource.class);
        
    @Inject
    private Com_destruction_certificateService com_destruction_certificateService;
    
    /**
     * POST  /com-destruction-certificates : Create a new com_destruction_certificate.
     *
     * @param com_destruction_certificate the com_destruction_certificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_destruction_certificate, or with status 400 (Bad Request) if the com_destruction_certificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-destruction-certificates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_destruction_certificate> createCom_destruction_certificate(@Valid @RequestBody Com_destruction_certificate com_destruction_certificate) throws URISyntaxException {
        log.debug("REST request to save Com_destruction_certificate : {}", com_destruction_certificate);
        if (com_destruction_certificate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_destruction_certificate", "idexists", "A new com_destruction_certificate cannot already have an ID")).body(null);
        }
        Com_destruction_certificate result = com_destruction_certificateService.save(com_destruction_certificate);
        return ResponseEntity.created(new URI("/api/com-destruction-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_destruction_certificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-destruction-certificates : Updates an existing com_destruction_certificate.
     *
     * @param com_destruction_certificate the com_destruction_certificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_destruction_certificate,
     * or with status 400 (Bad Request) if the com_destruction_certificate is not valid,
     * or with status 500 (Internal Server Error) if the com_destruction_certificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-destruction-certificates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_destruction_certificate> updateCom_destruction_certificate(@Valid @RequestBody Com_destruction_certificate com_destruction_certificate) throws URISyntaxException {
        log.debug("REST request to update Com_destruction_certificate : {}", com_destruction_certificate);
        if (com_destruction_certificate.getId() == null) {
            return createCom_destruction_certificate(com_destruction_certificate);
        }
        Com_destruction_certificate result = com_destruction_certificateService.save(com_destruction_certificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_destruction_certificate", com_destruction_certificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-destruction-certificates : get all the com_destruction_certificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_destruction_certificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-destruction-certificates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_destruction_certificate>> getAllCom_destruction_certificates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_destruction_certificates");
        Page<Com_destruction_certificate> page = com_destruction_certificateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-destruction-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-destruction-certificates/:id : get the "id" com_destruction_certificate.
     *
     * @param id the id of the com_destruction_certificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_destruction_certificate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-destruction-certificates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_destruction_certificate> getCom_destruction_certificate(@PathVariable Long id) {
        log.debug("REST request to get Com_destruction_certificate : {}", id);
        Com_destruction_certificate com_destruction_certificate = com_destruction_certificateService.findOne(id);
        return Optional.ofNullable(com_destruction_certificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-destruction-certificates/:id : delete the "id" com_destruction_certificate.
     *
     * @param id the id of the com_destruction_certificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-destruction-certificates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_destruction_certificate(@PathVariable Long id) {
        log.debug("REST request to delete Com_destruction_certificate : {}", id);
        com_destruction_certificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_destruction_certificate", id.toString())).build();
    }

}
