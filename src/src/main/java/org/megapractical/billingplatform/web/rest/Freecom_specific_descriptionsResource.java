package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_specific_descriptions;
import org.megapractical.billingplatform.service.Freecom_specific_descriptionsService;
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
 * REST controller for managing Freecom_specific_descriptions.
 */
@RestController
@RequestMapping("/api")
public class Freecom_specific_descriptionsResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_specific_descriptionsResource.class);
        
    @Inject
    private Freecom_specific_descriptionsService freecom_specific_descriptionsService;
    
    /**
     * POST  /freecom-specific-descriptions : Create a new freecom_specific_descriptions.
     *
     * @param freecom_specific_descriptions the freecom_specific_descriptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_specific_descriptions, or with status 400 (Bad Request) if the freecom_specific_descriptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-specific-descriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_specific_descriptions> createFreecom_specific_descriptions(@Valid @RequestBody Freecom_specific_descriptions freecom_specific_descriptions) throws URISyntaxException {
        log.debug("REST request to save Freecom_specific_descriptions : {}", freecom_specific_descriptions);
        if (freecom_specific_descriptions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_specific_descriptions", "idexists", "A new freecom_specific_descriptions cannot already have an ID")).body(null);
        }
        Freecom_specific_descriptions result = freecom_specific_descriptionsService.save(freecom_specific_descriptions);
        return ResponseEntity.created(new URI("/api/freecom-specific-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_specific_descriptions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-specific-descriptions : Updates an existing freecom_specific_descriptions.
     *
     * @param freecom_specific_descriptions the freecom_specific_descriptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_specific_descriptions,
     * or with status 400 (Bad Request) if the freecom_specific_descriptions is not valid,
     * or with status 500 (Internal Server Error) if the freecom_specific_descriptions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-specific-descriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_specific_descriptions> updateFreecom_specific_descriptions(@Valid @RequestBody Freecom_specific_descriptions freecom_specific_descriptions) throws URISyntaxException {
        log.debug("REST request to update Freecom_specific_descriptions : {}", freecom_specific_descriptions);
        if (freecom_specific_descriptions.getId() == null) {
            return createFreecom_specific_descriptions(freecom_specific_descriptions);
        }
        Freecom_specific_descriptions result = freecom_specific_descriptionsService.save(freecom_specific_descriptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_specific_descriptions", freecom_specific_descriptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-specific-descriptions : get all the freecom_specific_descriptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_specific_descriptions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-specific-descriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_specific_descriptions>> getAllFreecom_specific_descriptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_specific_descriptions");
        Page<Freecom_specific_descriptions> page = freecom_specific_descriptionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-specific-descriptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-specific-descriptions/:id : get the "id" freecom_specific_descriptions.
     *
     * @param id the id of the freecom_specific_descriptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_specific_descriptions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-specific-descriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_specific_descriptions> getFreecom_specific_descriptions(@PathVariable Long id) {
        log.debug("REST request to get Freecom_specific_descriptions : {}", id);
        Freecom_specific_descriptions freecom_specific_descriptions = freecom_specific_descriptionsService.findOne(id);
        return Optional.ofNullable(freecom_specific_descriptions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-specific-descriptions/:id : delete the "id" freecom_specific_descriptions.
     *
     * @param id the id of the freecom_specific_descriptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-specific-descriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_specific_descriptions(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_specific_descriptions : {}", id);
        freecom_specific_descriptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_specific_descriptions", id.toString())).build();
    }

}
