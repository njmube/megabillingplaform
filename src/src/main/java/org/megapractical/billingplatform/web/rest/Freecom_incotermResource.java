package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_incoterm;
import org.megapractical.billingplatform.service.Freecom_incotermService;
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
 * REST controller for managing Freecom_incoterm.
 */
@RestController
@RequestMapping("/api")
public class Freecom_incotermResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_incotermResource.class);
        
    @Inject
    private Freecom_incotermService freecom_incotermService;
    
    /**
     * POST  /freecom-incoterms : Create a new freecom_incoterm.
     *
     * @param freecom_incoterm the freecom_incoterm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_incoterm, or with status 400 (Bad Request) if the freecom_incoterm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-incoterms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_incoterm> createFreecom_incoterm(@Valid @RequestBody Freecom_incoterm freecom_incoterm) throws URISyntaxException {
        log.debug("REST request to save Freecom_incoterm : {}", freecom_incoterm);
        if (freecom_incoterm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_incoterm", "idexists", "A new freecom_incoterm cannot already have an ID")).body(null);
        }
        Freecom_incoterm result = freecom_incotermService.save(freecom_incoterm);
        return ResponseEntity.created(new URI("/api/freecom-incoterms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_incoterm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-incoterms : Updates an existing freecom_incoterm.
     *
     * @param freecom_incoterm the freecom_incoterm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_incoterm,
     * or with status 400 (Bad Request) if the freecom_incoterm is not valid,
     * or with status 500 (Internal Server Error) if the freecom_incoterm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-incoterms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_incoterm> updateFreecom_incoterm(@Valid @RequestBody Freecom_incoterm freecom_incoterm) throws URISyntaxException {
        log.debug("REST request to update Freecom_incoterm : {}", freecom_incoterm);
        if (freecom_incoterm.getId() == null) {
            return createFreecom_incoterm(freecom_incoterm);
        }
        Freecom_incoterm result = freecom_incotermService.save(freecom_incoterm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_incoterm", freecom_incoterm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-incoterms : get all the freecom_incoterms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_incoterms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-incoterms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_incoterm>> getAllFreecom_incoterms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_incoterms");
        Page<Freecom_incoterm> page = freecom_incotermService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-incoterms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-incoterms/:id : get the "id" freecom_incoterm.
     *
     * @param id the id of the freecom_incoterm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_incoterm, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-incoterms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_incoterm> getFreecom_incoterm(@PathVariable Long id) {
        log.debug("REST request to get Freecom_incoterm : {}", id);
        Freecom_incoterm freecom_incoterm = freecom_incotermService.findOne(id);
        return Optional.ofNullable(freecom_incoterm)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-incoterms/:id : delete the "id" freecom_incoterm.
     *
     * @param id the id of the freecom_incoterm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-incoterms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_incoterm(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_incoterm : {}", id);
        freecom_incotermService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_incoterm", id.toString())).build();
    }

}
