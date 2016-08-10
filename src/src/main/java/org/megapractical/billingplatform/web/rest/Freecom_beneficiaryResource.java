package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_beneficiary;
import org.megapractical.billingplatform.service.Freecom_beneficiaryService;
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
 * REST controller for managing Freecom_beneficiary.
 */
@RestController
@RequestMapping("/api")
public class Freecom_beneficiaryResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_beneficiaryResource.class);
        
    @Inject
    private Freecom_beneficiaryService freecom_beneficiaryService;
    
    /**
     * POST  /freecom-beneficiaries : Create a new freecom_beneficiary.
     *
     * @param freecom_beneficiary the freecom_beneficiary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_beneficiary, or with status 400 (Bad Request) if the freecom_beneficiary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-beneficiaries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_beneficiary> createFreecom_beneficiary(@Valid @RequestBody Freecom_beneficiary freecom_beneficiary) throws URISyntaxException {
        log.debug("REST request to save Freecom_beneficiary : {}", freecom_beneficiary);
        if (freecom_beneficiary.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_beneficiary", "idexists", "A new freecom_beneficiary cannot already have an ID")).body(null);
        }
        Freecom_beneficiary result = freecom_beneficiaryService.save(freecom_beneficiary);
        return ResponseEntity.created(new URI("/api/freecom-beneficiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_beneficiary", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-beneficiaries : Updates an existing freecom_beneficiary.
     *
     * @param freecom_beneficiary the freecom_beneficiary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_beneficiary,
     * or with status 400 (Bad Request) if the freecom_beneficiary is not valid,
     * or with status 500 (Internal Server Error) if the freecom_beneficiary couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-beneficiaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_beneficiary> updateFreecom_beneficiary(@Valid @RequestBody Freecom_beneficiary freecom_beneficiary) throws URISyntaxException {
        log.debug("REST request to update Freecom_beneficiary : {}", freecom_beneficiary);
        if (freecom_beneficiary.getId() == null) {
            return createFreecom_beneficiary(freecom_beneficiary);
        }
        Freecom_beneficiary result = freecom_beneficiaryService.save(freecom_beneficiary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_beneficiary", freecom_beneficiary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-beneficiaries : get all the freecom_beneficiaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_beneficiaries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-beneficiaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_beneficiary>> getAllFreecom_beneficiaries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_beneficiaries");
        Page<Freecom_beneficiary> page = freecom_beneficiaryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-beneficiaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-beneficiaries/:id : get the "id" freecom_beneficiary.
     *
     * @param id the id of the freecom_beneficiary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_beneficiary, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-beneficiaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_beneficiary> getFreecom_beneficiary(@PathVariable Long id) {
        log.debug("REST request to get Freecom_beneficiary : {}", id);
        Freecom_beneficiary freecom_beneficiary = freecom_beneficiaryService.findOne(id);
        return Optional.ofNullable(freecom_beneficiary)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-beneficiaries/:id : delete the "id" freecom_beneficiary.
     *
     * @param id the id of the freecom_beneficiary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-beneficiaries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_beneficiary(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_beneficiary : {}", id);
        freecom_beneficiaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_beneficiary", id.toString())).build();
    }

}
