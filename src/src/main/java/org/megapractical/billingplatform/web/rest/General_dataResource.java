package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.General_data;
import org.megapractical.billingplatform.service.General_dataService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing General_data.
 */
@RestController
@RequestMapping("/api")
public class General_dataResource {

    private final Logger log = LoggerFactory.getLogger(General_dataResource.class);

    @Inject
    private General_dataService general_dataService;

    /**
     * POST  /general-data : Create a new general_data.
     *
     * @param general_data the general_data to create
     * @return the ResponseEntity with status 201 (Created) and with body the new general_data, or with status 400 (Bad Request) if the general_data has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/general-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<General_data> createGeneral_data(@RequestBody General_data general_data) throws URISyntaxException {
        log.debug("REST request to save General_data : {}", general_data);
        if (general_data.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("general_data", "idexists", "A new general_data cannot already have an ID")).body(null);
        }
        General_data result = general_dataService.save(general_data);
        return ResponseEntity.created(new URI("/api/general-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("general_data", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /general-data : Updates an existing general_data.
     *
     * @param general_data the general_data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated general_data,
     * or with status 400 (Bad Request) if the general_data is not valid,
     * or with status 500 (Internal Server Error) if the general_data couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/general-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<General_data> updateGeneral_data(@RequestBody General_data general_data) throws URISyntaxException {
        log.debug("REST request to update General_data : {}", general_data);
        if (general_data.getId() == null) {
            return createGeneral_data(general_data);
        }
        General_data result = general_dataService.save(general_data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("general_data", general_data.getId().toString()))
            .body(result);
    }

    /**
     * GET  /general-data : get all the general_data.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of general_data in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/general-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<General_data>> getAllGeneral_data(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of General_data");
        Page<General_data> page = general_dataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/general-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /general-data/:id : get the "id" general_data.
     *
     * @param id the id of the general_data to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the general_data, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/general-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<General_data> getGeneral_data(@PathVariable Long id) {
        log.debug("REST request to get General_data : {}", id);
        if(id == 0){
            if(general_dataService.findAll().size() != 0){
                id = general_dataService.findAll().get(0).getId();
            }
        }
        General_data general_data = general_dataService.findOne(id);
        if(general_data == null){
            general_data = new General_data();
        }
        return Optional.ofNullable(general_data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /general-data/:id : delete the "id" general_data.
     *
     * @param id the id of the general_data to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/general-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGeneral_data(@PathVariable Long id) {
        log.debug("REST request to delete General_data : {}", id);
        general_dataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("general_data", id.toString())).build();
    }

}
