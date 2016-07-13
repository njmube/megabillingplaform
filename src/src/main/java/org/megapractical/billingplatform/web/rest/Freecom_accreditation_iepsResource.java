package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_accreditation_ieps;
import org.megapractical.billingplatform.service.Freecom_accreditation_iepsService;
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
 * REST controller for managing Freecom_accreditation_ieps.
 */
@RestController
@RequestMapping("/api")
public class Freecom_accreditation_iepsResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_accreditation_iepsResource.class);
        
    @Inject
    private Freecom_accreditation_iepsService freecom_accreditation_iepsService;
    
    /**
     * POST  /freecom-accreditation-ieps : Create a new freecom_accreditation_ieps.
     *
     * @param freecom_accreditation_ieps the freecom_accreditation_ieps to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_accreditation_ieps, or with status 400 (Bad Request) if the freecom_accreditation_ieps has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-accreditation-ieps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_accreditation_ieps> createFreecom_accreditation_ieps(@Valid @RequestBody Freecom_accreditation_ieps freecom_accreditation_ieps) throws URISyntaxException {
        log.debug("REST request to save Freecom_accreditation_ieps : {}", freecom_accreditation_ieps);
        if (freecom_accreditation_ieps.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_accreditation_ieps", "idexists", "A new freecom_accreditation_ieps cannot already have an ID")).body(null);
        }
        Freecom_accreditation_ieps result = freecom_accreditation_iepsService.save(freecom_accreditation_ieps);
        return ResponseEntity.created(new URI("/api/freecom-accreditation-ieps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_accreditation_ieps", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-accreditation-ieps : Updates an existing freecom_accreditation_ieps.
     *
     * @param freecom_accreditation_ieps the freecom_accreditation_ieps to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_accreditation_ieps,
     * or with status 400 (Bad Request) if the freecom_accreditation_ieps is not valid,
     * or with status 500 (Internal Server Error) if the freecom_accreditation_ieps couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-accreditation-ieps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_accreditation_ieps> updateFreecom_accreditation_ieps(@Valid @RequestBody Freecom_accreditation_ieps freecom_accreditation_ieps) throws URISyntaxException {
        log.debug("REST request to update Freecom_accreditation_ieps : {}", freecom_accreditation_ieps);
        if (freecom_accreditation_ieps.getId() == null) {
            return createFreecom_accreditation_ieps(freecom_accreditation_ieps);
        }
        Freecom_accreditation_ieps result = freecom_accreditation_iepsService.save(freecom_accreditation_ieps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_accreditation_ieps", freecom_accreditation_ieps.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-accreditation-ieps : get all the freecom_accreditation_ieps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_accreditation_ieps in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-accreditation-ieps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_accreditation_ieps>> getAllFreecom_accreditation_ieps(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_accreditation_ieps");
        Page<Freecom_accreditation_ieps> page = freecom_accreditation_iepsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-accreditation-ieps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-accreditation-ieps/:id : get the "id" freecom_accreditation_ieps.
     *
     * @param id the id of the freecom_accreditation_ieps to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_accreditation_ieps, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-accreditation-ieps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_accreditation_ieps> getFreecom_accreditation_ieps(@PathVariable Long id) {
        log.debug("REST request to get Freecom_accreditation_ieps : {}", id);
        Freecom_accreditation_ieps freecom_accreditation_ieps = freecom_accreditation_iepsService.findOne(id);
        return Optional.ofNullable(freecom_accreditation_ieps)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-accreditation-ieps/:id : delete the "id" freecom_accreditation_ieps.
     *
     * @param id the id of the freecom_accreditation_ieps to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-accreditation-ieps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_accreditation_ieps(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_accreditation_ieps : {}", id);
        freecom_accreditation_iepsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_accreditation_ieps", id.toString())).build();
    }

}
