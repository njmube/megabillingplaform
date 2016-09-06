package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_local_transfered;
import org.megapractical.billingplatform.service.Freecom_local_transferedService;
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
 * REST controller for managing Freecom_local_transfered.
 */
@RestController
@RequestMapping("/api")
public class Freecom_local_transferedResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_local_transferedResource.class);
        
    @Inject
    private Freecom_local_transferedService freecom_local_transferedService;
    
    /**
     * POST  /freecom-local-transfereds : Create a new freecom_local_transfered.
     *
     * @param freecom_local_transfered the freecom_local_transfered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_local_transfered, or with status 400 (Bad Request) if the freecom_local_transfered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-transfereds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_transfered> createFreecom_local_transfered(@Valid @RequestBody Freecom_local_transfered freecom_local_transfered) throws URISyntaxException {
        log.debug("REST request to save Freecom_local_transfered : {}", freecom_local_transfered);
        if (freecom_local_transfered.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_local_transfered", "idexists", "A new freecom_local_transfered cannot already have an ID")).body(null);
        }
        Freecom_local_transfered result = freecom_local_transferedService.save(freecom_local_transfered);
        return ResponseEntity.created(new URI("/api/freecom-local-transfereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_local_transfered", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-local-transfereds : Updates an existing freecom_local_transfered.
     *
     * @param freecom_local_transfered the freecom_local_transfered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_local_transfered,
     * or with status 400 (Bad Request) if the freecom_local_transfered is not valid,
     * or with status 500 (Internal Server Error) if the freecom_local_transfered couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-local-transfereds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_transfered> updateFreecom_local_transfered(@Valid @RequestBody Freecom_local_transfered freecom_local_transfered) throws URISyntaxException {
        log.debug("REST request to update Freecom_local_transfered : {}", freecom_local_transfered);
        if (freecom_local_transfered.getId() == null) {
            return createFreecom_local_transfered(freecom_local_transfered);
        }
        Freecom_local_transfered result = freecom_local_transferedService.save(freecom_local_transfered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_local_transfered", freecom_local_transfered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-local-transfereds : get all the freecom_local_transfereds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_local_transfereds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-local-transfereds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_local_transfered>> getAllFreecom_local_transfereds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_local_transfereds");
        Page<Freecom_local_transfered> page = freecom_local_transferedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-local-transfereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-local-transfereds/:id : get the "id" freecom_local_transfered.
     *
     * @param id the id of the freecom_local_transfered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_local_transfered, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-local-transfereds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_local_transfered> getFreecom_local_transfered(@PathVariable Long id) {
        log.debug("REST request to get Freecom_local_transfered : {}", id);
        Freecom_local_transfered freecom_local_transfered = freecom_local_transferedService.findOne(id);
        return Optional.ofNullable(freecom_local_transfered)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-local-transfereds/:id : delete the "id" freecom_local_transfered.
     *
     * @param id the id of the freecom_local_transfered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-local-transfereds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_local_transfered(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_local_transfered : {}", id);
        freecom_local_transferedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_local_transfered", id.toString())).build();
    }

}
