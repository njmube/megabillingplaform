package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_airline;
import org.megapractical.billingplatform.service.Freecom_airlineService;
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
 * REST controller for managing Freecom_airline.
 */
@RestController
@RequestMapping("/api")
public class Freecom_airlineResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_airlineResource.class);
        
    @Inject
    private Freecom_airlineService freecom_airlineService;
    
    /**
     * POST  /freecom-airlines : Create a new freecom_airline.
     *
     * @param freecom_airline the freecom_airline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_airline, or with status 400 (Bad Request) if the freecom_airline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-airlines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_airline> createFreecom_airline(@Valid @RequestBody Freecom_airline freecom_airline) throws URISyntaxException {
        log.debug("REST request to save Freecom_airline : {}", freecom_airline);
        if (freecom_airline.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_airline", "idexists", "A new freecom_airline cannot already have an ID")).body(null);
        }
        Freecom_airline result = freecom_airlineService.save(freecom_airline);
        return ResponseEntity.created(new URI("/api/freecom-airlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_airline", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-airlines : Updates an existing freecom_airline.
     *
     * @param freecom_airline the freecom_airline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_airline,
     * or with status 400 (Bad Request) if the freecom_airline is not valid,
     * or with status 500 (Internal Server Error) if the freecom_airline couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-airlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_airline> updateFreecom_airline(@Valid @RequestBody Freecom_airline freecom_airline) throws URISyntaxException {
        log.debug("REST request to update Freecom_airline : {}", freecom_airline);
        if (freecom_airline.getId() == null) {
            return createFreecom_airline(freecom_airline);
        }
        Freecom_airline result = freecom_airlineService.save(freecom_airline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_airline", freecom_airline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-airlines : get all the freecom_airlines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_airlines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-airlines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_airline>> getAllFreecom_airlines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_airlines");
        Page<Freecom_airline> page = freecom_airlineService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-airlines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-airlines/:id : get the "id" freecom_airline.
     *
     * @param id the id of the freecom_airline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_airline, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-airlines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_airline> getFreecom_airline(@PathVariable Long id) {
        log.debug("REST request to get Freecom_airline : {}", id);
        Freecom_airline freecom_airline = freecom_airlineService.findOne(id);
        return Optional.ofNullable(freecom_airline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-airlines/:id : delete the "id" freecom_airline.
     *
     * @param id the id of the freecom_airline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-airlines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_airline(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_airline : {}", id);
        freecom_airlineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_airline", id.toString())).build();
    }

}
