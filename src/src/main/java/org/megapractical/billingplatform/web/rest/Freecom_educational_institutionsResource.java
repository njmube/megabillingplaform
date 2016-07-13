package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_educational_institutions;
import org.megapractical.billingplatform.service.Freecom_educational_institutionsService;
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
 * REST controller for managing Freecom_educational_institutions.
 */
@RestController
@RequestMapping("/api")
public class Freecom_educational_institutionsResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_educational_institutionsResource.class);
        
    @Inject
    private Freecom_educational_institutionsService freecom_educational_institutionsService;
    
    /**
     * POST  /freecom-educational-institutions : Create a new freecom_educational_institutions.
     *
     * @param freecom_educational_institutions the freecom_educational_institutions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_educational_institutions, or with status 400 (Bad Request) if the freecom_educational_institutions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-educational-institutions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_educational_institutions> createFreecom_educational_institutions(@Valid @RequestBody Freecom_educational_institutions freecom_educational_institutions) throws URISyntaxException {
        log.debug("REST request to save Freecom_educational_institutions : {}", freecom_educational_institutions);
        if (freecom_educational_institutions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_educational_institutions", "idexists", "A new freecom_educational_institutions cannot already have an ID")).body(null);
        }
        Freecom_educational_institutions result = freecom_educational_institutionsService.save(freecom_educational_institutions);
        return ResponseEntity.created(new URI("/api/freecom-educational-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_educational_institutions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-educational-institutions : Updates an existing freecom_educational_institutions.
     *
     * @param freecom_educational_institutions the freecom_educational_institutions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_educational_institutions,
     * or with status 400 (Bad Request) if the freecom_educational_institutions is not valid,
     * or with status 500 (Internal Server Error) if the freecom_educational_institutions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-educational-institutions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_educational_institutions> updateFreecom_educational_institutions(@Valid @RequestBody Freecom_educational_institutions freecom_educational_institutions) throws URISyntaxException {
        log.debug("REST request to update Freecom_educational_institutions : {}", freecom_educational_institutions);
        if (freecom_educational_institutions.getId() == null) {
            return createFreecom_educational_institutions(freecom_educational_institutions);
        }
        Freecom_educational_institutions result = freecom_educational_institutionsService.save(freecom_educational_institutions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_educational_institutions", freecom_educational_institutions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-educational-institutions : get all the freecom_educational_institutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_educational_institutions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-educational-institutions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_educational_institutions>> getAllFreecom_educational_institutions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_educational_institutions");
        Page<Freecom_educational_institutions> page = freecom_educational_institutionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-educational-institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-educational-institutions/:id : get the "id" freecom_educational_institutions.
     *
     * @param id the id of the freecom_educational_institutions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_educational_institutions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-educational-institutions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_educational_institutions> getFreecom_educational_institutions(@PathVariable Long id) {
        log.debug("REST request to get Freecom_educational_institutions : {}", id);
        Freecom_educational_institutions freecom_educational_institutions = freecom_educational_institutionsService.findOne(id);
        return Optional.ofNullable(freecom_educational_institutions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-educational-institutions/:id : delete the "id" freecom_educational_institutions.
     *
     * @param id the id of the freecom_educational_institutions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-educational-institutions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_educational_institutions(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_educational_institutions : {}", id);
        freecom_educational_institutionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_educational_institutions", id.toString())).build();
    }

}
