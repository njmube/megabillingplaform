package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_tax_retentions;
import org.megapractical.billingplatform.service.Free_tax_retentionsService;
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
 * REST controller for managing Free_tax_retentions.
 */
@RestController
@RequestMapping("/api")
public class Free_tax_retentionsResource {

    private final Logger log = LoggerFactory.getLogger(Free_tax_retentionsResource.class);
        
    @Inject
    private Free_tax_retentionsService free_tax_retentionsService;
    
    /**
     * POST  /free-tax-retentions : Create a new free_tax_retentions.
     *
     * @param free_tax_retentions the free_tax_retentions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_tax_retentions, or with status 400 (Bad Request) if the free_tax_retentions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-tax-retentions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_retentions> createFree_tax_retentions(@Valid @RequestBody Free_tax_retentions free_tax_retentions) throws URISyntaxException {
        log.debug("REST request to save Free_tax_retentions : {}", free_tax_retentions);
        if (free_tax_retentions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_tax_retentions", "idexists", "A new free_tax_retentions cannot already have an ID")).body(null);
        }
        Free_tax_retentions result = free_tax_retentionsService.save(free_tax_retentions);
        return ResponseEntity.created(new URI("/api/free-tax-retentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_tax_retentions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-tax-retentions : Updates an existing free_tax_retentions.
     *
     * @param free_tax_retentions the free_tax_retentions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_tax_retentions,
     * or with status 400 (Bad Request) if the free_tax_retentions is not valid,
     * or with status 500 (Internal Server Error) if the free_tax_retentions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-tax-retentions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_retentions> updateFree_tax_retentions(@Valid @RequestBody Free_tax_retentions free_tax_retentions) throws URISyntaxException {
        log.debug("REST request to update Free_tax_retentions : {}", free_tax_retentions);
        if (free_tax_retentions.getId() == null) {
            return createFree_tax_retentions(free_tax_retentions);
        }
        Free_tax_retentions result = free_tax_retentionsService.save(free_tax_retentions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_tax_retentions", free_tax_retentions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-tax-retentions : get all the free_tax_retentions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_tax_retentions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-tax-retentions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_tax_retentions>> getAllFree_tax_retentions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_tax_retentions");
        Page<Free_tax_retentions> page = free_tax_retentionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-tax-retentions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-tax-retentions/:id : get the "id" free_tax_retentions.
     *
     * @param id the id of the free_tax_retentions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_tax_retentions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-tax-retentions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_retentions> getFree_tax_retentions(@PathVariable Long id) {
        log.debug("REST request to get Free_tax_retentions : {}", id);
        Free_tax_retentions free_tax_retentions = free_tax_retentionsService.findOne(id);
        return Optional.ofNullable(free_tax_retentions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-tax-retentions/:id : delete the "id" free_tax_retentions.
     *
     * @param id the id of the free_tax_retentions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-tax-retentions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_tax_retentions(@PathVariable Long id) {
        log.debug("REST request to delete Free_tax_retentions : {}", id);
        free_tax_retentionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_tax_retentions", id.toString())).build();
    }

}
