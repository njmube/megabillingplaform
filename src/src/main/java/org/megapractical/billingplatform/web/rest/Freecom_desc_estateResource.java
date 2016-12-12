package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_desc_estate;
import org.megapractical.billingplatform.service.Freecom_desc_estateService;
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
 * REST controller for managing Freecom_desc_estate.
 */
@RestController
@RequestMapping("/api")
public class Freecom_desc_estateResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_desc_estateResource.class);
        
    @Inject
    private Freecom_desc_estateService freecom_desc_estateService;
    
    /**
     * POST  /freecom-desc-estates : Create a new freecom_desc_estate.
     *
     * @param freecom_desc_estate the freecom_desc_estate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_desc_estate, or with status 400 (Bad Request) if the freecom_desc_estate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-desc-estates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_desc_estate> createFreecom_desc_estate(@Valid @RequestBody Freecom_desc_estate freecom_desc_estate) throws URISyntaxException {
        log.debug("REST request to save Freecom_desc_estate : {}", freecom_desc_estate);
        if (freecom_desc_estate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_desc_estate", "idexists", "A new freecom_desc_estate cannot already have an ID")).body(null);
        }
        Freecom_desc_estate result = freecom_desc_estateService.save(freecom_desc_estate);
        return ResponseEntity.created(new URI("/api/freecom-desc-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_desc_estate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-desc-estates : Updates an existing freecom_desc_estate.
     *
     * @param freecom_desc_estate the freecom_desc_estate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_desc_estate,
     * or with status 400 (Bad Request) if the freecom_desc_estate is not valid,
     * or with status 500 (Internal Server Error) if the freecom_desc_estate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-desc-estates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_desc_estate> updateFreecom_desc_estate(@Valid @RequestBody Freecom_desc_estate freecom_desc_estate) throws URISyntaxException {
        log.debug("REST request to update Freecom_desc_estate : {}", freecom_desc_estate);
        if (freecom_desc_estate.getId() == null) {
            return createFreecom_desc_estate(freecom_desc_estate);
        }
        Freecom_desc_estate result = freecom_desc_estateService.save(freecom_desc_estate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_desc_estate", freecom_desc_estate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-desc-estates : get all the freecom_desc_estates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_desc_estates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-desc-estates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_desc_estate>> getAllFreecom_desc_estates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_desc_estates");
        Page<Freecom_desc_estate> page = freecom_desc_estateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-desc-estates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-desc-estates/:id : get the "id" freecom_desc_estate.
     *
     * @param id the id of the freecom_desc_estate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_desc_estate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-desc-estates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_desc_estate> getFreecom_desc_estate(@PathVariable Long id) {
        log.debug("REST request to get Freecom_desc_estate : {}", id);
        Freecom_desc_estate freecom_desc_estate = freecom_desc_estateService.findOne(id);
        return Optional.ofNullable(freecom_desc_estate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-desc-estates/:id : delete the "id" freecom_desc_estate.
     *
     * @param id the id of the freecom_desc_estate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-desc-estates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_desc_estate(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_desc_estate : {}", id);
        freecom_desc_estateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_desc_estate", id.toString())).build();
    }

}
