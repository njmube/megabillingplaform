package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_foreign_tourist_passenger;
import org.megapractical.billingplatform.service.Freecom_foreign_tourist_passengerService;
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
 * REST controller for managing Freecom_foreign_tourist_passenger.
 */
@RestController
@RequestMapping("/api")
public class Freecom_foreign_tourist_passengerResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_foreign_tourist_passengerResource.class);
        
    @Inject
    private Freecom_foreign_tourist_passengerService freecom_foreign_tourist_passengerService;
    
    /**
     * POST  /freecom-foreign-tourist-passengers : Create a new freecom_foreign_tourist_passenger.
     *
     * @param freecom_foreign_tourist_passenger the freecom_foreign_tourist_passenger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_foreign_tourist_passenger, or with status 400 (Bad Request) if the freecom_foreign_tourist_passenger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-tourist-passengers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_tourist_passenger> createFreecom_foreign_tourist_passenger(@Valid @RequestBody Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger) throws URISyntaxException {
        log.debug("REST request to save Freecom_foreign_tourist_passenger : {}", freecom_foreign_tourist_passenger);
        if (freecom_foreign_tourist_passenger.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_foreign_tourist_passenger", "idexists", "A new freecom_foreign_tourist_passenger cannot already have an ID")).body(null);
        }
        Freecom_foreign_tourist_passenger result = freecom_foreign_tourist_passengerService.save(freecom_foreign_tourist_passenger);
        return ResponseEntity.created(new URI("/api/freecom-foreign-tourist-passengers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_foreign_tourist_passenger", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-foreign-tourist-passengers : Updates an existing freecom_foreign_tourist_passenger.
     *
     * @param freecom_foreign_tourist_passenger the freecom_foreign_tourist_passenger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_foreign_tourist_passenger,
     * or with status 400 (Bad Request) if the freecom_foreign_tourist_passenger is not valid,
     * or with status 500 (Internal Server Error) if the freecom_foreign_tourist_passenger couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-foreign-tourist-passengers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_tourist_passenger> updateFreecom_foreign_tourist_passenger(@Valid @RequestBody Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger) throws URISyntaxException {
        log.debug("REST request to update Freecom_foreign_tourist_passenger : {}", freecom_foreign_tourist_passenger);
        if (freecom_foreign_tourist_passenger.getId() == null) {
            return createFreecom_foreign_tourist_passenger(freecom_foreign_tourist_passenger);
        }
        Freecom_foreign_tourist_passenger result = freecom_foreign_tourist_passengerService.save(freecom_foreign_tourist_passenger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_foreign_tourist_passenger", freecom_foreign_tourist_passenger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-foreign-tourist-passengers : get all the freecom_foreign_tourist_passengers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_foreign_tourist_passengers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-foreign-tourist-passengers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_foreign_tourist_passenger>> getAllFreecom_foreign_tourist_passengers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_foreign_tourist_passengers");
        Page<Freecom_foreign_tourist_passenger> page = freecom_foreign_tourist_passengerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-foreign-tourist-passengers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-foreign-tourist-passengers/:id : get the "id" freecom_foreign_tourist_passenger.
     *
     * @param id the id of the freecom_foreign_tourist_passenger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_foreign_tourist_passenger, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-foreign-tourist-passengers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_foreign_tourist_passenger> getFreecom_foreign_tourist_passenger(@PathVariable Long id) {
        log.debug("REST request to get Freecom_foreign_tourist_passenger : {}", id);
        Freecom_foreign_tourist_passenger freecom_foreign_tourist_passenger = freecom_foreign_tourist_passengerService.findOne(id);
        return Optional.ofNullable(freecom_foreign_tourist_passenger)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-foreign-tourist-passengers/:id : delete the "id" freecom_foreign_tourist_passenger.
     *
     * @param id the id of the freecom_foreign_tourist_passenger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-foreign-tourist-passengers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_foreign_tourist_passenger(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_foreign_tourist_passenger : {}", id);
        freecom_foreign_tourist_passengerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_foreign_tourist_passenger", id.toString())).build();
    }

}
