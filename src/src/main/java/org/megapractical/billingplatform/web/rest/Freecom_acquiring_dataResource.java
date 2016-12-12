package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_acquiring_data;
import org.megapractical.billingplatform.service.Freecom_acquiring_dataService;
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
 * REST controller for managing Freecom_acquiring_data.
 */
@RestController
@RequestMapping("/api")
public class Freecom_acquiring_dataResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_acquiring_dataResource.class);
        
    @Inject
    private Freecom_acquiring_dataService freecom_acquiring_dataService;
    
    /**
     * POST  /freecom-acquiring-data : Create a new freecom_acquiring_data.
     *
     * @param freecom_acquiring_data the freecom_acquiring_data to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_acquiring_data, or with status 400 (Bad Request) if the freecom_acquiring_data has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-acquiring-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_acquiring_data> createFreecom_acquiring_data(@Valid @RequestBody Freecom_acquiring_data freecom_acquiring_data) throws URISyntaxException {
        log.debug("REST request to save Freecom_acquiring_data : {}", freecom_acquiring_data);
        if (freecom_acquiring_data.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_acquiring_data", "idexists", "A new freecom_acquiring_data cannot already have an ID")).body(null);
        }
        Freecom_acquiring_data result = freecom_acquiring_dataService.save(freecom_acquiring_data);
        return ResponseEntity.created(new URI("/api/freecom-acquiring-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_acquiring_data", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-acquiring-data : Updates an existing freecom_acquiring_data.
     *
     * @param freecom_acquiring_data the freecom_acquiring_data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_acquiring_data,
     * or with status 400 (Bad Request) if the freecom_acquiring_data is not valid,
     * or with status 500 (Internal Server Error) if the freecom_acquiring_data couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-acquiring-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_acquiring_data> updateFreecom_acquiring_data(@Valid @RequestBody Freecom_acquiring_data freecom_acquiring_data) throws URISyntaxException {
        log.debug("REST request to update Freecom_acquiring_data : {}", freecom_acquiring_data);
        if (freecom_acquiring_data.getId() == null) {
            return createFreecom_acquiring_data(freecom_acquiring_data);
        }
        Freecom_acquiring_data result = freecom_acquiring_dataService.save(freecom_acquiring_data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_acquiring_data", freecom_acquiring_data.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-acquiring-data : get all the freecom_acquiring_data.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_acquiring_data in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-acquiring-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_acquiring_data>> getAllFreecom_acquiring_data(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_acquiring_data");
        Page<Freecom_acquiring_data> page = freecom_acquiring_dataService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-acquiring-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-acquiring-data/:id : get the "id" freecom_acquiring_data.
     *
     * @param id the id of the freecom_acquiring_data to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_acquiring_data, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-acquiring-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_acquiring_data> getFreecom_acquiring_data(@PathVariable Long id) {
        log.debug("REST request to get Freecom_acquiring_data : {}", id);
        Freecom_acquiring_data freecom_acquiring_data = freecom_acquiring_dataService.findOne(id);
        return Optional.ofNullable(freecom_acquiring_data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-acquiring-data/:id : delete the "id" freecom_acquiring_data.
     *
     * @param id the id of the freecom_acquiring_data to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-acquiring-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_acquiring_data(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_acquiring_data : {}", id);
        freecom_acquiring_dataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_acquiring_data", id.toString())).build();
    }

}
