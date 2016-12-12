package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_dataunacquiring;
import org.megapractical.billingplatform.service.Freecom_dataunacquiringService;
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
 * REST controller for managing Freecom_dataunacquiring.
 */
@RestController
@RequestMapping("/api")
public class Freecom_dataunacquiringResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_dataunacquiringResource.class);
        
    @Inject
    private Freecom_dataunacquiringService freecom_dataunacquiringService;
    
    /**
     * POST  /freecom-dataunacquirings : Create a new freecom_dataunacquiring.
     *
     * @param freecom_dataunacquiring the freecom_dataunacquiring to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_dataunacquiring, or with status 400 (Bad Request) if the freecom_dataunacquiring has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataunacquirings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunacquiring> createFreecom_dataunacquiring(@Valid @RequestBody Freecom_dataunacquiring freecom_dataunacquiring) throws URISyntaxException {
        log.debug("REST request to save Freecom_dataunacquiring : {}", freecom_dataunacquiring);
        if (freecom_dataunacquiring.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_dataunacquiring", "idexists", "A new freecom_dataunacquiring cannot already have an ID")).body(null);
        }
        Freecom_dataunacquiring result = freecom_dataunacquiringService.save(freecom_dataunacquiring);
        return ResponseEntity.created(new URI("/api/freecom-dataunacquirings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_dataunacquiring", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-dataunacquirings : Updates an existing freecom_dataunacquiring.
     *
     * @param freecom_dataunacquiring the freecom_dataunacquiring to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_dataunacquiring,
     * or with status 400 (Bad Request) if the freecom_dataunacquiring is not valid,
     * or with status 500 (Internal Server Error) if the freecom_dataunacquiring couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-dataunacquirings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunacquiring> updateFreecom_dataunacquiring(@Valid @RequestBody Freecom_dataunacquiring freecom_dataunacquiring) throws URISyntaxException {
        log.debug("REST request to update Freecom_dataunacquiring : {}", freecom_dataunacquiring);
        if (freecom_dataunacquiring.getId() == null) {
            return createFreecom_dataunacquiring(freecom_dataunacquiring);
        }
        Freecom_dataunacquiring result = freecom_dataunacquiringService.save(freecom_dataunacquiring);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_dataunacquiring", freecom_dataunacquiring.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-dataunacquirings : get all the freecom_dataunacquirings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_dataunacquirings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-dataunacquirings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_dataunacquiring>> getAllFreecom_dataunacquirings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_dataunacquirings");
        Page<Freecom_dataunacquiring> page = freecom_dataunacquiringService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-dataunacquirings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-dataunacquirings/:id : get the "id" freecom_dataunacquiring.
     *
     * @param id the id of the freecom_dataunacquiring to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_dataunacquiring, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-dataunacquirings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_dataunacquiring> getFreecom_dataunacquiring(@PathVariable Long id) {
        log.debug("REST request to get Freecom_dataunacquiring : {}", id);
        Freecom_dataunacquiring freecom_dataunacquiring = freecom_dataunacquiringService.findOne(id);
        return Optional.ofNullable(freecom_dataunacquiring)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-dataunacquirings/:id : delete the "id" freecom_dataunacquiring.
     *
     * @param id the id of the freecom_dataunacquiring to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-dataunacquirings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_dataunacquiring(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_dataunacquiring : {}", id);
        freecom_dataunacquiringService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_dataunacquiring", id.toString())).build();
    }

}
