package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_notary_data;
import org.megapractical.billingplatform.service.Freecom_notary_dataService;
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
 * REST controller for managing Freecom_notary_data.
 */
@RestController
@RequestMapping("/api")
public class Freecom_notary_dataResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_notary_dataResource.class);
        
    @Inject
    private Freecom_notary_dataService freecom_notary_dataService;
    
    /**
     * POST  /freecom-notary-data : Create a new freecom_notary_data.
     *
     * @param freecom_notary_data the freecom_notary_data to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_notary_data, or with status 400 (Bad Request) if the freecom_notary_data has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-notary-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_notary_data> createFreecom_notary_data(@Valid @RequestBody Freecom_notary_data freecom_notary_data) throws URISyntaxException {
        log.debug("REST request to save Freecom_notary_data : {}", freecom_notary_data);
        if (freecom_notary_data.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_notary_data", "idexists", "A new freecom_notary_data cannot already have an ID")).body(null);
        }
        Freecom_notary_data result = freecom_notary_dataService.save(freecom_notary_data);
        return ResponseEntity.created(new URI("/api/freecom-notary-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_notary_data", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-notary-data : Updates an existing freecom_notary_data.
     *
     * @param freecom_notary_data the freecom_notary_data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_notary_data,
     * or with status 400 (Bad Request) if the freecom_notary_data is not valid,
     * or with status 500 (Internal Server Error) if the freecom_notary_data couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-notary-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_notary_data> updateFreecom_notary_data(@Valid @RequestBody Freecom_notary_data freecom_notary_data) throws URISyntaxException {
        log.debug("REST request to update Freecom_notary_data : {}", freecom_notary_data);
        if (freecom_notary_data.getId() == null) {
            return createFreecom_notary_data(freecom_notary_data);
        }
        Freecom_notary_data result = freecom_notary_dataService.save(freecom_notary_data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_notary_data", freecom_notary_data.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-notary-data : get all the freecom_notary_data.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_notary_data in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-notary-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_notary_data>> getAllFreecom_notary_data(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_notary_data");
        Page<Freecom_notary_data> page = freecom_notary_dataService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-notary-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-notary-data/:id : get the "id" freecom_notary_data.
     *
     * @param id the id of the freecom_notary_data to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_notary_data, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-notary-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_notary_data> getFreecom_notary_data(@PathVariable Long id) {
        log.debug("REST request to get Freecom_notary_data : {}", id);
        Freecom_notary_data freecom_notary_data = freecom_notary_dataService.findOne(id);
        return Optional.ofNullable(freecom_notary_data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-notary-data/:id : delete the "id" freecom_notary_data.
     *
     * @param id the id of the freecom_notary_data to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-notary-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_notary_data(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_notary_data : {}", id);
        freecom_notary_dataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_notary_data", id.toString())).build();
    }

}
