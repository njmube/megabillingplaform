package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_local_retentions;
import org.megapractical.billingplatform.service.Freecom_local_retentionsService;
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
 * REST controller for managing Freecom_local_retentions.
 */
@RestController
@RequestMapping("/api")
public class Freecom_local_retentionsResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_local_retentionsResource.class);
        
    @Inject
    private Freecom_local_retentionsService freecom_local_retentionsService;
    
    /**
     * POST  /freecom-local-retentions : Create a new freecom_local_retentions.
     *
     * @param freecom_local_retentions the freecom_local_retentions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_local_retentions, or with status 400 (Bad Request) if the freecom_local_retentions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-retentions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_retentions> createFreecom_local_retentions(@Valid @RequestBody Freecom_local_retentions freecom_local_retentions) throws URISyntaxException {
        log.debug("REST request to save Freecom_local_retentions : {}", freecom_local_retentions);
        if (freecom_local_retentions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_local_retentions", "idexists", "A new freecom_local_retentions cannot already have an ID")).body(null);
        }
        Freecom_local_retentions result = freecom_local_retentionsService.save(freecom_local_retentions);
        return ResponseEntity.created(new URI("/api/freecom-local-retentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_local_retentions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-local-retentions : Updates an existing freecom_local_retentions.
     *
     * @param freecom_local_retentions the freecom_local_retentions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_local_retentions,
     * or with status 400 (Bad Request) if the freecom_local_retentions is not valid,
     * or with status 500 (Internal Server Error) if the freecom_local_retentions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-retentions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_retentions> updateFreecom_local_retentions(@Valid @RequestBody Freecom_local_retentions freecom_local_retentions) throws URISyntaxException {
        log.debug("REST request to update Freecom_local_retentions : {}", freecom_local_retentions);
        if (freecom_local_retentions.getId() == null) {
            return createFreecom_local_retentions(freecom_local_retentions);
        }
        Freecom_local_retentions result = freecom_local_retentionsService.save(freecom_local_retentions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_local_retentions", freecom_local_retentions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-local-retentions : get all the freecom_local_retentions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_local_retentions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-local-retentions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_local_retentions>> getAllFreecom_local_retentions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_local_retentions");
        Page<Freecom_local_retentions> page = freecom_local_retentionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-local-retentions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-local-retentions/:id : get the "id" freecom_local_retentions.
     *
     * @param id the id of the freecom_local_retentions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_local_retentions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-local-retentions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_retentions> getFreecom_local_retentions(@PathVariable Long id) {
        log.debug("REST request to get Freecom_local_retentions : {}", id);
        Freecom_local_retentions freecom_local_retentions = freecom_local_retentionsService.findOne(id);
        return Optional.ofNullable(freecom_local_retentions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-local-retentions/:id : delete the "id" freecom_local_retentions.
     *
     * @param id the id of the freecom_local_retentions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-local-retentions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_local_retentions(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_local_retentions : {}", id);
        freecom_local_retentionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_local_retentions", id.toString())).build();
    }

}
