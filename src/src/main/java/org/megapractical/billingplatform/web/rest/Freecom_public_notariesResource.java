package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_public_notaries;
import org.megapractical.billingplatform.service.Freecom_public_notariesService;
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
 * REST controller for managing Freecom_public_notaries.
 */
@RestController
@RequestMapping("/api")
public class Freecom_public_notariesResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_public_notariesResource.class);
        
    @Inject
    private Freecom_public_notariesService freecom_public_notariesService;
    
    /**
     * POST  /freecom-public-notaries : Create a new freecom_public_notaries.
     *
     * @param freecom_public_notaries the freecom_public_notaries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_public_notaries, or with status 400 (Bad Request) if the freecom_public_notaries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-public-notaries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_public_notaries> createFreecom_public_notaries(@Valid @RequestBody Freecom_public_notaries freecom_public_notaries) throws URISyntaxException {
        log.debug("REST request to save Freecom_public_notaries : {}", freecom_public_notaries);
        if (freecom_public_notaries.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_public_notaries", "idexists", "A new freecom_public_notaries cannot already have an ID")).body(null);
        }
        Freecom_public_notaries result = freecom_public_notariesService.save(freecom_public_notaries);
        return ResponseEntity.created(new URI("/api/freecom-public-notaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_public_notaries", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-public-notaries : Updates an existing freecom_public_notaries.
     *
     * @param freecom_public_notaries the freecom_public_notaries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_public_notaries,
     * or with status 400 (Bad Request) if the freecom_public_notaries is not valid,
     * or with status 500 (Internal Server Error) if the freecom_public_notaries couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-public-notaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_public_notaries> updateFreecom_public_notaries(@Valid @RequestBody Freecom_public_notaries freecom_public_notaries) throws URISyntaxException {
        log.debug("REST request to update Freecom_public_notaries : {}", freecom_public_notaries);
        if (freecom_public_notaries.getId() == null) {
            return createFreecom_public_notaries(freecom_public_notaries);
        }
        Freecom_public_notaries result = freecom_public_notariesService.save(freecom_public_notaries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_public_notaries", freecom_public_notaries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-public-notaries : get all the freecom_public_notaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_public_notaries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-public-notaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_public_notaries>> getAllFreecom_public_notaries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_public_notaries");
        Page<Freecom_public_notaries> page = freecom_public_notariesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-public-notaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-public-notaries/:id : get the "id" freecom_public_notaries.
     *
     * @param id the id of the freecom_public_notaries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_public_notaries, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-public-notaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_public_notaries> getFreecom_public_notaries(@PathVariable Long id) {
        log.debug("REST request to get Freecom_public_notaries : {}", id);
        Freecom_public_notaries freecom_public_notaries = freecom_public_notariesService.findOne(id);
        return Optional.ofNullable(freecom_public_notaries)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-public-notaries/:id : delete the "id" freecom_public_notaries.
     *
     * @param id the id of the freecom_public_notaries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-public-notaries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_public_notaries(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_public_notaries : {}", id);
        freecom_public_notariesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_public_notaries", id.toString())).build();
    }

}
