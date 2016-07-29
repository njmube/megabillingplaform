package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_retentions_transfered;
import org.megapractical.billingplatform.service.Freecom_retentions_transferedService;
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
 * REST controller for managing Freecom_retentions_transfered.
 */
@RestController
@RequestMapping("/api")
public class Freecom_retentions_transferedResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_retentions_transferedResource.class);
        
    @Inject
    private Freecom_retentions_transferedService freecom_retentions_transferedService;
    
    /**
     * POST  /freecom-retentions-transfereds : Create a new freecom_retentions_transfered.
     *
     * @param freecom_retentions_transfered the freecom_retentions_transfered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_retentions_transfered, or with status 400 (Bad Request) if the freecom_retentions_transfered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-retentions-transfereds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_retentions_transfered> createFreecom_retentions_transfered(@Valid @RequestBody Freecom_retentions_transfered freecom_retentions_transfered) throws URISyntaxException {
        log.debug("REST request to save Freecom_retentions_transfered : {}", freecom_retentions_transfered);
        if (freecom_retentions_transfered.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_retentions_transfered", "idexists", "A new freecom_retentions_transfered cannot already have an ID")).body(null);
        }
        Freecom_retentions_transfered result = freecom_retentions_transferedService.save(freecom_retentions_transfered);
        return ResponseEntity.created(new URI("/api/freecom-retentions-transfereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_retentions_transfered", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-retentions-transfereds : Updates an existing freecom_retentions_transfered.
     *
     * @param freecom_retentions_transfered the freecom_retentions_transfered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_retentions_transfered,
     * or with status 400 (Bad Request) if the freecom_retentions_transfered is not valid,
     * or with status 500 (Internal Server Error) if the freecom_retentions_transfered couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-retentions-transfereds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_retentions_transfered> updateFreecom_retentions_transfered(@Valid @RequestBody Freecom_retentions_transfered freecom_retentions_transfered) throws URISyntaxException {
        log.debug("REST request to update Freecom_retentions_transfered : {}", freecom_retentions_transfered);
        if (freecom_retentions_transfered.getId() == null) {
            return createFreecom_retentions_transfered(freecom_retentions_transfered);
        }
        Freecom_retentions_transfered result = freecom_retentions_transferedService.save(freecom_retentions_transfered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_retentions_transfered", freecom_retentions_transfered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-retentions-transfereds : get all the freecom_retentions_transfereds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_retentions_transfereds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-retentions-transfereds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_retentions_transfered>> getAllFreecom_retentions_transfereds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_retentions_transfereds");
        Page<Freecom_retentions_transfered> page = freecom_retentions_transferedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-retentions-transfereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-retentions-transfereds/:id : get the "id" freecom_retentions_transfered.
     *
     * @param id the id of the freecom_retentions_transfered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_retentions_transfered, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-retentions-transfereds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_retentions_transfered> getFreecom_retentions_transfered(@PathVariable Long id) {
        log.debug("REST request to get Freecom_retentions_transfered : {}", id);
        Freecom_retentions_transfered freecom_retentions_transfered = freecom_retentions_transferedService.findOne(id);
        return Optional.ofNullable(freecom_retentions_transfered)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-retentions-transfereds/:id : delete the "id" freecom_retentions_transfered.
     *
     * @param id the id of the freecom_retentions_transfered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-retentions-transfereds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_retentions_transfered(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_retentions_transfered : {}", id);
        freecom_retentions_transferedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_retentions_transfered", id.toString())).build();
    }

}
