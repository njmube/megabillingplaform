package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_charge;
import org.megapractical.billingplatform.service.Freecom_chargeService;
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
 * REST controller for managing Freecom_charge.
 */
@RestController
@RequestMapping("/api")
public class Freecom_chargeResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_chargeResource.class);
        
    @Inject
    private Freecom_chargeService freecom_chargeService;
    
    /**
     * POST  /freecom-charges : Create a new freecom_charge.
     *
     * @param freecom_charge the freecom_charge to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_charge, or with status 400 (Bad Request) if the freecom_charge has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-charges",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_charge> createFreecom_charge(@Valid @RequestBody Freecom_charge freecom_charge) throws URISyntaxException {
        log.debug("REST request to save Freecom_charge : {}", freecom_charge);
        if (freecom_charge.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_charge", "idexists", "A new freecom_charge cannot already have an ID")).body(null);
        }
        Freecom_charge result = freecom_chargeService.save(freecom_charge);
        return ResponseEntity.created(new URI("/api/freecom-charges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_charge", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-charges : Updates an existing freecom_charge.
     *
     * @param freecom_charge the freecom_charge to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_charge,
     * or with status 400 (Bad Request) if the freecom_charge is not valid,
     * or with status 500 (Internal Server Error) if the freecom_charge couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-charges",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_charge> updateFreecom_charge(@Valid @RequestBody Freecom_charge freecom_charge) throws URISyntaxException {
        log.debug("REST request to update Freecom_charge : {}", freecom_charge);
        if (freecom_charge.getId() == null) {
            return createFreecom_charge(freecom_charge);
        }
        Freecom_charge result = freecom_chargeService.save(freecom_charge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_charge", freecom_charge.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-charges : get all the freecom_charges.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_charges in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-charges",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_charge>> getAllFreecom_charges(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_charges");
        Page<Freecom_charge> page = freecom_chargeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-charges");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-charges/:id : get the "id" freecom_charge.
     *
     * @param id the id of the freecom_charge to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_charge, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-charges/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_charge> getFreecom_charge(@PathVariable Long id) {
        log.debug("REST request to get Freecom_charge : {}", id);
        Freecom_charge freecom_charge = freecom_chargeService.findOne(id);
        return Optional.ofNullable(freecom_charge)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-charges/:id : delete the "id" freecom_charge.
     *
     * @param id the id of the freecom_charge to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-charges/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_charge(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_charge : {}", id);
        freecom_chargeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_charge", id.toString())).build();
    }

}
