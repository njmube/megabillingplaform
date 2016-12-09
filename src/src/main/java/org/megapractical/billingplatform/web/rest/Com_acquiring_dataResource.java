package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_acquiring_data;
import org.megapractical.billingplatform.service.Com_acquiring_dataService;
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
 * REST controller for managing Com_acquiring_data.
 */
@RestController
@RequestMapping("/api")
public class Com_acquiring_dataResource {

    private final Logger log = LoggerFactory.getLogger(Com_acquiring_dataResource.class);
        
    @Inject
    private Com_acquiring_dataService com_acquiring_dataService;
    
    /**
     * POST  /com-acquiring-data : Create a new com_acquiring_data.
     *
     * @param com_acquiring_data the com_acquiring_data to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_acquiring_data, or with status 400 (Bad Request) if the com_acquiring_data has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-acquiring-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_acquiring_data> createCom_acquiring_data(@Valid @RequestBody Com_acquiring_data com_acquiring_data) throws URISyntaxException {
        log.debug("REST request to save Com_acquiring_data : {}", com_acquiring_data);
        if (com_acquiring_data.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_acquiring_data", "idexists", "A new com_acquiring_data cannot already have an ID")).body(null);
        }
        Com_acquiring_data result = com_acquiring_dataService.save(com_acquiring_data);
        return ResponseEntity.created(new URI("/api/com-acquiring-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_acquiring_data", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-acquiring-data : Updates an existing com_acquiring_data.
     *
     * @param com_acquiring_data the com_acquiring_data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_acquiring_data,
     * or with status 400 (Bad Request) if the com_acquiring_data is not valid,
     * or with status 500 (Internal Server Error) if the com_acquiring_data couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-acquiring-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_acquiring_data> updateCom_acquiring_data(@Valid @RequestBody Com_acquiring_data com_acquiring_data) throws URISyntaxException {
        log.debug("REST request to update Com_acquiring_data : {}", com_acquiring_data);
        if (com_acquiring_data.getId() == null) {
            return createCom_acquiring_data(com_acquiring_data);
        }
        Com_acquiring_data result = com_acquiring_dataService.save(com_acquiring_data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_acquiring_data", com_acquiring_data.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-acquiring-data : get all the com_acquiring_data.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_acquiring_data in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-acquiring-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_acquiring_data>> getAllCom_acquiring_data(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_acquiring_data");
        Page<Com_acquiring_data> page = com_acquiring_dataService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-acquiring-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-acquiring-data/:id : get the "id" com_acquiring_data.
     *
     * @param id the id of the com_acquiring_data to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_acquiring_data, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-acquiring-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_acquiring_data> getCom_acquiring_data(@PathVariable Long id) {
        log.debug("REST request to get Com_acquiring_data : {}", id);
        Com_acquiring_data com_acquiring_data = com_acquiring_dataService.findOne(id);
        return Optional.ofNullable(com_acquiring_data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-acquiring-data/:id : delete the "id" com_acquiring_data.
     *
     * @param id the id of the com_acquiring_data to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-acquiring-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_acquiring_data(@PathVariable Long id) {
        log.debug("REST request to delete Com_acquiring_data : {}", id);
        com_acquiring_dataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_acquiring_data", id.toString())).build();
    }

}
