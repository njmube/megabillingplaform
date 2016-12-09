package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_notary_data;
import org.megapractical.billingplatform.service.Com_notary_dataService;
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
 * REST controller for managing Com_notary_data.
 */
@RestController
@RequestMapping("/api")
public class Com_notary_dataResource {

    private final Logger log = LoggerFactory.getLogger(Com_notary_dataResource.class);
        
    @Inject
    private Com_notary_dataService com_notary_dataService;
    
    /**
     * POST  /com-notary-data : Create a new com_notary_data.
     *
     * @param com_notary_data the com_notary_data to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_notary_data, or with status 400 (Bad Request) if the com_notary_data has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-notary-data",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_notary_data> createCom_notary_data(@Valid @RequestBody Com_notary_data com_notary_data) throws URISyntaxException {
        log.debug("REST request to save Com_notary_data : {}", com_notary_data);
        if (com_notary_data.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_notary_data", "idexists", "A new com_notary_data cannot already have an ID")).body(null);
        }
        Com_notary_data result = com_notary_dataService.save(com_notary_data);
        return ResponseEntity.created(new URI("/api/com-notary-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_notary_data", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-notary-data : Updates an existing com_notary_data.
     *
     * @param com_notary_data the com_notary_data to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_notary_data,
     * or with status 400 (Bad Request) if the com_notary_data is not valid,
     * or with status 500 (Internal Server Error) if the com_notary_data couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-notary-data",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_notary_data> updateCom_notary_data(@Valid @RequestBody Com_notary_data com_notary_data) throws URISyntaxException {
        log.debug("REST request to update Com_notary_data : {}", com_notary_data);
        if (com_notary_data.getId() == null) {
            return createCom_notary_data(com_notary_data);
        }
        Com_notary_data result = com_notary_dataService.save(com_notary_data);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_notary_data", com_notary_data.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-notary-data : get all the com_notary_data.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_notary_data in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-notary-data",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_notary_data>> getAllCom_notary_data(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_notary_data");
        Page<Com_notary_data> page = com_notary_dataService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-notary-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-notary-data/:id : get the "id" com_notary_data.
     *
     * @param id the id of the com_notary_data to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_notary_data, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-notary-data/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_notary_data> getCom_notary_data(@PathVariable Long id) {
        log.debug("REST request to get Com_notary_data : {}", id);
        Com_notary_data com_notary_data = com_notary_dataService.findOne(id);
        return Optional.ofNullable(com_notary_data)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-notary-data/:id : delete the "id" com_notary_data.
     *
     * @param id the id of the com_notary_data to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-notary-data/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_notary_data(@PathVariable Long id) {
        log.debug("REST request to delete Com_notary_data : {}", id);
        com_notary_dataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_notary_data", id.toString())).build();
    }

}
