package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_payer;
import org.megapractical.billingplatform.service.Freecom_payerService;
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
 * REST controller for managing Freecom_payer.
 */
@RestController
@RequestMapping("/api")
public class Freecom_payerResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_payerResource.class);
        
    @Inject
    private Freecom_payerService freecom_payerService;
    
    /**
     * POST  /freecom-payers : Create a new freecom_payer.
     *
     * @param freecom_payer the freecom_payer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_payer, or with status 400 (Bad Request) if the freecom_payer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-payers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_payer> createFreecom_payer(@Valid @RequestBody Freecom_payer freecom_payer) throws URISyntaxException {
        log.debug("REST request to save Freecom_payer : {}", freecom_payer);
        if (freecom_payer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_payer", "idexists", "A new freecom_payer cannot already have an ID")).body(null);
        }
        Freecom_payer result = freecom_payerService.save(freecom_payer);
        return ResponseEntity.created(new URI("/api/freecom-payers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_payer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-payers : Updates an existing freecom_payer.
     *
     * @param freecom_payer the freecom_payer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_payer,
     * or with status 400 (Bad Request) if the freecom_payer is not valid,
     * or with status 500 (Internal Server Error) if the freecom_payer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-payers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_payer> updateFreecom_payer(@Valid @RequestBody Freecom_payer freecom_payer) throws URISyntaxException {
        log.debug("REST request to update Freecom_payer : {}", freecom_payer);
        if (freecom_payer.getId() == null) {
            return createFreecom_payer(freecom_payer);
        }
        Freecom_payer result = freecom_payerService.save(freecom_payer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_payer", freecom_payer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-payers : get all the freecom_payers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_payers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-payers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_payer>> getAllFreecom_payers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_payers");
        Page<Freecom_payer> page = freecom_payerService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-payers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-payers/:id : get the "id" freecom_payer.
     *
     * @param id the id of the freecom_payer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_payer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-payers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_payer> getFreecom_payer(@PathVariable Long id) {
        log.debug("REST request to get Freecom_payer : {}", id);
        Freecom_payer freecom_payer = freecom_payerService.findOne(id);
        return Optional.ofNullable(freecom_payer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-payers/:id : delete the "id" freecom_payer.
     *
     * @param id the id of the freecom_payer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-payers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_payer(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_payer : {}", id);
        freecom_payerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_payer", id.toString())).build();
    }

}
