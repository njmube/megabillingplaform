package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_airline;
import org.megapractical.billingplatform.service.Com_airlineService;
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
 * REST controller for managing Com_airline.
 */
@RestController
@RequestMapping("/api")
public class Com_airlineResource {

    private final Logger log = LoggerFactory.getLogger(Com_airlineResource.class);
        
    @Inject
    private Com_airlineService com_airlineService;
    
    /**
     * POST  /com-airlines : Create a new com_airline.
     *
     * @param com_airline the com_airline to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_airline, or with status 400 (Bad Request) if the com_airline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-airlines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_airline> createCom_airline(@Valid @RequestBody Com_airline com_airline) throws URISyntaxException {
        log.debug("REST request to save Com_airline : {}", com_airline);
        if (com_airline.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_airline", "idexists", "A new com_airline cannot already have an ID")).body(null);
        }
        Com_airline result = com_airlineService.save(com_airline);
        return ResponseEntity.created(new URI("/api/com-airlines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_airline", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-airlines : Updates an existing com_airline.
     *
     * @param com_airline the com_airline to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_airline,
     * or with status 400 (Bad Request) if the com_airline is not valid,
     * or with status 500 (Internal Server Error) if the com_airline couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-airlines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_airline> updateCom_airline(@Valid @RequestBody Com_airline com_airline) throws URISyntaxException {
        log.debug("REST request to update Com_airline : {}", com_airline);
        if (com_airline.getId() == null) {
            return createCom_airline(com_airline);
        }
        Com_airline result = com_airlineService.save(com_airline);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_airline", com_airline.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-airlines : get all the com_airlines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_airlines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-airlines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_airline>> getAllCom_airlines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_airlines");
        Page<Com_airline> page = com_airlineService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-airlines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-airlines/:id : get the "id" com_airline.
     *
     * @param id the id of the com_airline to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_airline, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-airlines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_airline> getCom_airline(@PathVariable Long id) {
        log.debug("REST request to get Com_airline : {}", id);
        Com_airline com_airline = com_airlineService.findOne(id);
        return Optional.ofNullable(com_airline)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-airlines/:id : delete the "id" com_airline.
     *
     * @param id the id of the com_airline to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-airlines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_airline(@PathVariable Long id) {
        log.debug("REST request to delete Com_airline : {}", id);
        com_airlineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_airline", id.toString())).build();
    }

}
