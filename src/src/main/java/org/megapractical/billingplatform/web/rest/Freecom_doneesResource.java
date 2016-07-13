package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_donees;
import org.megapractical.billingplatform.service.Freecom_doneesService;
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
 * REST controller for managing Freecom_donees.
 */
@RestController
@RequestMapping("/api")
public class Freecom_doneesResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_doneesResource.class);
        
    @Inject
    private Freecom_doneesService freecom_doneesService;
    
    /**
     * POST  /freecom-donees : Create a new freecom_donees.
     *
     * @param freecom_donees the freecom_donees to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_donees, or with status 400 (Bad Request) if the freecom_donees has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-donees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_donees> createFreecom_donees(@Valid @RequestBody Freecom_donees freecom_donees) throws URISyntaxException {
        log.debug("REST request to save Freecom_donees : {}", freecom_donees);
        if (freecom_donees.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_donees", "idexists", "A new freecom_donees cannot already have an ID")).body(null);
        }
        Freecom_donees result = freecom_doneesService.save(freecom_donees);
        return ResponseEntity.created(new URI("/api/freecom-donees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_donees", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-donees : Updates an existing freecom_donees.
     *
     * @param freecom_donees the freecom_donees to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_donees,
     * or with status 400 (Bad Request) if the freecom_donees is not valid,
     * or with status 500 (Internal Server Error) if the freecom_donees couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-donees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_donees> updateFreecom_donees(@Valid @RequestBody Freecom_donees freecom_donees) throws URISyntaxException {
        log.debug("REST request to update Freecom_donees : {}", freecom_donees);
        if (freecom_donees.getId() == null) {
            return createFreecom_donees(freecom_donees);
        }
        Freecom_donees result = freecom_doneesService.save(freecom_donees);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_donees", freecom_donees.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-donees : get all the freecom_donees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_donees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-donees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_donees>> getAllFreecom_donees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_donees");
        Page<Freecom_donees> page = freecom_doneesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-donees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-donees/:id : get the "id" freecom_donees.
     *
     * @param id the id of the freecom_donees to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_donees, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-donees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_donees> getFreecom_donees(@PathVariable Long id) {
        log.debug("REST request to get Freecom_donees : {}", id);
        Freecom_donees freecom_donees = freecom_doneesService.findOne(id);
        return Optional.ofNullable(freecom_donees)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-donees/:id : delete the "id" freecom_donees.
     *
     * @param id the id of the freecom_donees to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-donees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_donees(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_donees : {}", id);
        freecom_doneesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_donees", id.toString())).build();
    }

}
