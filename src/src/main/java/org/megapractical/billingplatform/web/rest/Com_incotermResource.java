package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_incoterm;
import org.megapractical.billingplatform.service.Com_incotermService;
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
 * REST controller for managing Com_incoterm.
 */
@RestController
@RequestMapping("/api")
public class Com_incotermResource {

    private final Logger log = LoggerFactory.getLogger(Com_incotermResource.class);
        
    @Inject
    private Com_incotermService com_incotermService;
    
    /**
     * POST  /com-incoterms : Create a new com_incoterm.
     *
     * @param com_incoterm the com_incoterm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_incoterm, or with status 400 (Bad Request) if the com_incoterm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-incoterms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_incoterm> createCom_incoterm(@Valid @RequestBody Com_incoterm com_incoterm) throws URISyntaxException {
        log.debug("REST request to save Com_incoterm : {}", com_incoterm);
        if (com_incoterm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_incoterm", "idexists", "A new com_incoterm cannot already have an ID")).body(null);
        }
        Com_incoterm result = com_incotermService.save(com_incoterm);
        return ResponseEntity.created(new URI("/api/com-incoterms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_incoterm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-incoterms : Updates an existing com_incoterm.
     *
     * @param com_incoterm the com_incoterm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_incoterm,
     * or with status 400 (Bad Request) if the com_incoterm is not valid,
     * or with status 500 (Internal Server Error) if the com_incoterm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-incoterms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_incoterm> updateCom_incoterm(@Valid @RequestBody Com_incoterm com_incoterm) throws URISyntaxException {
        log.debug("REST request to update Com_incoterm : {}", com_incoterm);
        if (com_incoterm.getId() == null) {
            return createCom_incoterm(com_incoterm);
        }
        Com_incoterm result = com_incotermService.save(com_incoterm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_incoterm", com_incoterm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-incoterms : get all the com_incoterms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_incoterms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-incoterms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_incoterm>> getAllCom_incoterms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_incoterms");
        Page<Com_incoterm> page = com_incotermService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-incoterms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-incoterms/:id : get the "id" com_incoterm.
     *
     * @param id the id of the com_incoterm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_incoterm, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-incoterms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_incoterm> getCom_incoterm(@PathVariable Long id) {
        log.debug("REST request to get Com_incoterm : {}", id);
        Com_incoterm com_incoterm = com_incotermService.findOne(id);
        return Optional.ofNullable(com_incoterm)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-incoterms/:id : delete the "id" com_incoterm.
     *
     * @param id the id of the com_incoterm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-incoterms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_incoterm(@PathVariable Long id) {
        log.debug("REST request to delete Com_incoterm : {}", id);
        com_incotermService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_incoterm", id.toString())).build();
    }

}
