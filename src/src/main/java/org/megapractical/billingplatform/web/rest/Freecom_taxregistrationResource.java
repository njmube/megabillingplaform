package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_taxregistration;
import org.megapractical.billingplatform.service.Freecom_taxregistrationService;
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
 * REST controller for managing Freecom_taxregistration.
 */
@RestController
@RequestMapping("/api")
public class Freecom_taxregistrationResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_taxregistrationResource.class);
        
    @Inject
    private Freecom_taxregistrationService freecom_taxregistrationService;
    
    /**
     * POST  /freecom-taxregistrations : Create a new freecom_taxregistration.
     *
     * @param freecom_taxregistration the freecom_taxregistration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_taxregistration, or with status 400 (Bad Request) if the freecom_taxregistration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-taxregistrations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxregistration> createFreecom_taxregistration(@Valid @RequestBody Freecom_taxregistration freecom_taxregistration) throws URISyntaxException {
        log.debug("REST request to save Freecom_taxregistration : {}", freecom_taxregistration);
        if (freecom_taxregistration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_taxregistration", "idexists", "A new freecom_taxregistration cannot already have an ID")).body(null);
        }
        Freecom_taxregistration result = freecom_taxregistrationService.save(freecom_taxregistration);
        return ResponseEntity.created(new URI("/api/freecom-taxregistrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_taxregistration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-taxregistrations : Updates an existing freecom_taxregistration.
     *
     * @param freecom_taxregistration the freecom_taxregistration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_taxregistration,
     * or with status 400 (Bad Request) if the freecom_taxregistration is not valid,
     * or with status 500 (Internal Server Error) if the freecom_taxregistration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-taxregistrations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxregistration> updateFreecom_taxregistration(@Valid @RequestBody Freecom_taxregistration freecom_taxregistration) throws URISyntaxException {
        log.debug("REST request to update Freecom_taxregistration : {}", freecom_taxregistration);
        if (freecom_taxregistration.getId() == null) {
            return createFreecom_taxregistration(freecom_taxregistration);
        }
        Freecom_taxregistration result = freecom_taxregistrationService.save(freecom_taxregistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_taxregistration", freecom_taxregistration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-taxregistrations : get all the freecom_taxregistrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_taxregistrations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-taxregistrations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_taxregistration>> getAllFreecom_taxregistrations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_taxregistrations");
        Page<Freecom_taxregistration> page = freecom_taxregistrationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-taxregistrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-taxregistrations/:id : get the "id" freecom_taxregistration.
     *
     * @param id the id of the freecom_taxregistration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_taxregistration, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-taxregistrations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxregistration> getFreecom_taxregistration(@PathVariable Long id) {
        log.debug("REST request to get Freecom_taxregistration : {}", id);
        Freecom_taxregistration freecom_taxregistration = freecom_taxregistrationService.findOne(id);
        return Optional.ofNullable(freecom_taxregistration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-taxregistrations/:id : delete the "id" freecom_taxregistration.
     *
     * @param id the id of the freecom_taxregistration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-taxregistrations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_taxregistration(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_taxregistration : {}", id);
        freecom_taxregistrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_taxregistration", id.toString())).build();
    }

}
