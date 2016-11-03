package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_foreign_tourist_passenger;
import org.megapractical.billingplatform.service.Com_foreign_tourist_passengerService;
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
 * REST controller for managing Com_foreign_tourist_passenger.
 */
@RestController
@RequestMapping("/api")
public class Com_foreign_tourist_passengerResource {

    private final Logger log = LoggerFactory.getLogger(Com_foreign_tourist_passengerResource.class);
        
    @Inject
    private Com_foreign_tourist_passengerService com_foreign_tourist_passengerService;
    
    /**
     * POST  /com-foreign-tourist-passengers : Create a new com_foreign_tourist_passenger.
     *
     * @param com_foreign_tourist_passenger the com_foreign_tourist_passenger to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_foreign_tourist_passenger, or with status 400 (Bad Request) if the com_foreign_tourist_passenger has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-tourist-passengers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_tourist_passenger> createCom_foreign_tourist_passenger(@Valid @RequestBody Com_foreign_tourist_passenger com_foreign_tourist_passenger) throws URISyntaxException {
        log.debug("REST request to save Com_foreign_tourist_passenger : {}", com_foreign_tourist_passenger);
        if (com_foreign_tourist_passenger.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_foreign_tourist_passenger", "idexists", "A new com_foreign_tourist_passenger cannot already have an ID")).body(null);
        }
        Com_foreign_tourist_passenger result = com_foreign_tourist_passengerService.save(com_foreign_tourist_passenger);
        return ResponseEntity.created(new URI("/api/com-foreign-tourist-passengers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_foreign_tourist_passenger", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-foreign-tourist-passengers : Updates an existing com_foreign_tourist_passenger.
     *
     * @param com_foreign_tourist_passenger the com_foreign_tourist_passenger to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_foreign_tourist_passenger,
     * or with status 400 (Bad Request) if the com_foreign_tourist_passenger is not valid,
     * or with status 500 (Internal Server Error) if the com_foreign_tourist_passenger couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-foreign-tourist-passengers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_tourist_passenger> updateCom_foreign_tourist_passenger(@Valid @RequestBody Com_foreign_tourist_passenger com_foreign_tourist_passenger) throws URISyntaxException {
        log.debug("REST request to update Com_foreign_tourist_passenger : {}", com_foreign_tourist_passenger);
        if (com_foreign_tourist_passenger.getId() == null) {
            return createCom_foreign_tourist_passenger(com_foreign_tourist_passenger);
        }
        Com_foreign_tourist_passenger result = com_foreign_tourist_passengerService.save(com_foreign_tourist_passenger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_foreign_tourist_passenger", com_foreign_tourist_passenger.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-foreign-tourist-passengers : get all the com_foreign_tourist_passengers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_foreign_tourist_passengers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-foreign-tourist-passengers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_foreign_tourist_passenger>> getAllCom_foreign_tourist_passengers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_foreign_tourist_passengers");
        Page<Com_foreign_tourist_passenger> page = com_foreign_tourist_passengerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-foreign-tourist-passengers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-foreign-tourist-passengers/:id : get the "id" com_foreign_tourist_passenger.
     *
     * @param id the id of the com_foreign_tourist_passenger to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_foreign_tourist_passenger, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-foreign-tourist-passengers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_foreign_tourist_passenger> getCom_foreign_tourist_passenger(@PathVariable Long id) {
        log.debug("REST request to get Com_foreign_tourist_passenger : {}", id);
        Com_foreign_tourist_passenger com_foreign_tourist_passenger = com_foreign_tourist_passengerService.findOne(id);
        return Optional.ofNullable(com_foreign_tourist_passenger)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-foreign-tourist-passengers/:id : delete the "id" com_foreign_tourist_passenger.
     *
     * @param id the id of the com_foreign_tourist_passenger to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-foreign-tourist-passengers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_foreign_tourist_passenger(@PathVariable Long id) {
        log.debug("REST request to delete Com_foreign_tourist_passenger : {}", id);
        com_foreign_tourist_passengerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_foreign_tourist_passenger", id.toString())).build();
    }

}
