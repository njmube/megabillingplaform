package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_certificate;
import org.megapractical.billingplatform.service.Taxpayer_certificateService;
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
 * REST controller for managing Taxpayer_certificate.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_certificateResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_certificateResource.class);

    @Inject
    private Taxpayer_certificateService taxpayer_certificateService;

    /**
     * POST  /taxpayer-certificates : Create a new taxpayer_certificate.
     *
     * @param taxpayer_certificate the taxpayer_certificate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_certificate, or with status 400 (Bad Request) if the taxpayer_certificate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-certificates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_certificate> createTaxpayer_certificate(@Valid @RequestBody Taxpayer_certificate taxpayer_certificate) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_certificate : {}", taxpayer_certificate);
        if (taxpayer_certificate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_certificate", "idexists", "A new taxpayer_certificate cannot already have an ID")).body(null);
        }
        Taxpayer_certificate result = taxpayer_certificateService.save(taxpayer_certificate, "rfc");
        return ResponseEntity.created(new URI("/api/taxpayer-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_certificate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-certificates : Updates an existing taxpayer_certificate.
     *
     * @param taxpayer_certificate the taxpayer_certificate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_certificate,
     * or with status 400 (Bad Request) if the taxpayer_certificate is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_certificate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-certificates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_certificate> updateTaxpayer_certificate(@Valid @RequestBody Taxpayer_certificate taxpayer_certificate) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_certificate : {}", taxpayer_certificate);
        if (taxpayer_certificate.getId() == null) {
            return createTaxpayer_certificate(taxpayer_certificate);
        }
        Taxpayer_certificate result = taxpayer_certificateService.save(taxpayer_certificate, "rfc");
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_certificate", taxpayer_certificate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-certificates : get all the taxpayer_certificates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_certificates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-certificates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_certificate>> getAllTaxpayer_certificates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_certificates");
        Page<Taxpayer_certificate> page = taxpayer_certificateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-certificates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-certificates/:id : get the "id" taxpayer_certificate.
     *
     * @param id the id of the taxpayer_certificate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_certificate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-certificates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_certificate> getTaxpayer_certificate(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_certificate : {}", id);
        Taxpayer_certificate taxpayer_certificate = taxpayer_certificateService.findOne(id);
        return Optional.ofNullable(taxpayer_certificate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-certificates/:id : delete the "id" taxpayer_certificate.
     *
     * @param id the id of the taxpayer_certificate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-certificates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_certificate(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_certificate : {}", id);
        taxpayer_certificateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_certificate", id.toString())).build();
    }

}
