package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Complement;
import org.megapractical.billingplatform.service.ComplementService;
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
 * REST controller for managing Complement.
 */
@RestController
@RequestMapping("/api")
public class ComplementResource {

    private final Logger log = LoggerFactory.getLogger(ComplementResource.class);
        
    @Inject
    private ComplementService complementService;
    
    /**
     * POST  /complements : Create a new complement.
     *
     * @param complement the complement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new complement, or with status 400 (Bad Request) if the complement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/complements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complement> createComplement(@Valid @RequestBody Complement complement) throws URISyntaxException {
        log.debug("REST request to save Complement : {}", complement);
        if (complement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("complement", "idexists", "A new complement cannot already have an ID")).body(null);
        }
        Complement result = complementService.save(complement);
        return ResponseEntity.created(new URI("/api/complements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("complement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /complements : Updates an existing complement.
     *
     * @param complement the complement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated complement,
     * or with status 400 (Bad Request) if the complement is not valid,
     * or with status 500 (Internal Server Error) if the complement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/complements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complement> updateComplement(@Valid @RequestBody Complement complement) throws URISyntaxException {
        log.debug("REST request to update Complement : {}", complement);
        if (complement.getId() == null) {
            return createComplement(complement);
        }
        Complement result = complementService.save(complement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("complement", complement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /complements : get all the complements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of complements in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/complements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Complement>> getAllComplements(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Complements");
        Page<Complement> page = complementService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/complements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /complements/:id : get the "id" complement.
     *
     * @param id the id of the complement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the complement, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/complements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Complement> getComplement(@PathVariable Long id) {
        log.debug("REST request to get Complement : {}", id);
        Complement complement = complementService.findOne(id);
        return Optional.ofNullable(complement)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /complements/:id : delete the "id" complement.
     *
     * @param id the id of the complement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/complements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteComplement(@PathVariable Long id) {
        log.debug("REST request to delete Complement : {}", id);
        complementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("complement", id.toString())).build();
    }

}
