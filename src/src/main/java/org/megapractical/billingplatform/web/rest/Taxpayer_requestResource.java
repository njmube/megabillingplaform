package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Taxpayer_request;
import org.megapractical.billingplatform.service.Taxpayer_requestService;
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
 * REST controller for managing Taxpayer_request.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_requestResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_requestResource.class);
        
    @Inject
    private Taxpayer_requestService taxpayer_requestService;
    
    /**
     * POST  /taxpayer-requests : Create a new taxpayer_request.
     *
     * @param taxpayer_request the taxpayer_request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_request, or with status 400 (Bad Request) if the taxpayer_request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-requests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request> createTaxpayer_request(@Valid @RequestBody Taxpayer_request taxpayer_request) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_request : {}", taxpayer_request);
        if (taxpayer_request.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_request", "idexists", "A new taxpayer_request cannot already have an ID")).body(null);
        }
        Taxpayer_request result = taxpayer_requestService.save(taxpayer_request);
        return ResponseEntity.created(new URI("/api/taxpayer-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_request", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-requests : Updates an existing taxpayer_request.
     *
     * @param taxpayer_request the taxpayer_request to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_request,
     * or with status 400 (Bad Request) if the taxpayer_request is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_request couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-requests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request> updateTaxpayer_request(@Valid @RequestBody Taxpayer_request taxpayer_request) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_request : {}", taxpayer_request);
        if (taxpayer_request.getId() == null) {
            return createTaxpayer_request(taxpayer_request);
        }
        Taxpayer_request result = taxpayer_requestService.save(taxpayer_request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_request", taxpayer_request.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-requests : get all the taxpayer_requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_requests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-requests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Taxpayer_request>> getAllTaxpayer_requests(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_requests");
        Page<Taxpayer_request> page = taxpayer_requestService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-requests/:id : get the "id" taxpayer_request.
     *
     * @param id the id of the taxpayer_request to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_request, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-requests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_request> getTaxpayer_request(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_request : {}", id);
        Taxpayer_request taxpayer_request = taxpayer_requestService.findOne(id);
        return Optional.ofNullable(taxpayer_request)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-requests/:id : delete the "id" taxpayer_request.
     *
     * @param id the id of the taxpayer_request to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-requests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_request(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_request : {}", id);
        taxpayer_requestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_request", id.toString())).build();
    }

}
