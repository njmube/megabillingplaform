package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_addressee;
import org.megapractical.billingplatform.service.Freecom_addresseeService;
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
 * REST controller for managing Freecom_addressee.
 */
@RestController
@RequestMapping("/api")
public class Freecom_addresseeResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_addresseeResource.class);
        
    @Inject
    private Freecom_addresseeService freecom_addresseeService;
    
    /**
     * POST  /freecom-addressees : Create a new freecom_addressee.
     *
     * @param freecom_addressee the freecom_addressee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_addressee, or with status 400 (Bad Request) if the freecom_addressee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-addressees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_addressee> createFreecom_addressee(@Valid @RequestBody Freecom_addressee freecom_addressee) throws URISyntaxException {
        log.debug("REST request to save Freecom_addressee : {}", freecom_addressee);
        if (freecom_addressee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_addressee", "idexists", "A new freecom_addressee cannot already have an ID")).body(null);
        }
        Freecom_addressee result = freecom_addresseeService.save(freecom_addressee);
        return ResponseEntity.created(new URI("/api/freecom-addressees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_addressee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-addressees : Updates an existing freecom_addressee.
     *
     * @param freecom_addressee the freecom_addressee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_addressee,
     * or with status 400 (Bad Request) if the freecom_addressee is not valid,
     * or with status 500 (Internal Server Error) if the freecom_addressee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-addressees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_addressee> updateFreecom_addressee(@Valid @RequestBody Freecom_addressee freecom_addressee) throws URISyntaxException {
        log.debug("REST request to update Freecom_addressee : {}", freecom_addressee);
        if (freecom_addressee.getId() == null) {
            return createFreecom_addressee(freecom_addressee);
        }
        Freecom_addressee result = freecom_addresseeService.save(freecom_addressee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_addressee", freecom_addressee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-addressees : get all the freecom_addressees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_addressees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-addressees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_addressee>> getAllFreecom_addressees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_addressees");
        Page<Freecom_addressee> page = freecom_addresseeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-addressees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-addressees/:id : get the "id" freecom_addressee.
     *
     * @param id the id of the freecom_addressee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_addressee, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-addressees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_addressee> getFreecom_addressee(@PathVariable Long id) {
        log.debug("REST request to get Freecom_addressee : {}", id);
        Freecom_addressee freecom_addressee = freecom_addresseeService.findOne(id);
        return Optional.ofNullable(freecom_addressee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-addressees/:id : delete the "id" freecom_addressee.
     *
     * @param id the id of the freecom_addressee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-addressees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_addressee(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_addressee : {}", id);
        freecom_addresseeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_addressee", id.toString())).build();
    }

}
