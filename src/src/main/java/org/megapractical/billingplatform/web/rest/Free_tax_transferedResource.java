package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_tax_transfered;
import org.megapractical.billingplatform.service.Free_tax_transferedService;
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
 * REST controller for managing Free_tax_transfered.
 */
@RestController
@RequestMapping("/api")
public class Free_tax_transferedResource {

    private final Logger log = LoggerFactory.getLogger(Free_tax_transferedResource.class);
        
    @Inject
    private Free_tax_transferedService free_tax_transferedService;
    
    /**
     * POST  /free-tax-transfereds : Create a new free_tax_transfered.
     *
     * @param free_tax_transfered the free_tax_transfered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_tax_transfered, or with status 400 (Bad Request) if the free_tax_transfered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-tax-transfereds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_transfered> createFree_tax_transfered(@Valid @RequestBody Free_tax_transfered free_tax_transfered) throws URISyntaxException {
        log.debug("REST request to save Free_tax_transfered : {}", free_tax_transfered);
        if (free_tax_transfered.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_tax_transfered", "idexists", "A new free_tax_transfered cannot already have an ID")).body(null);
        }
        Free_tax_transfered result = free_tax_transferedService.save(free_tax_transfered);
        return ResponseEntity.created(new URI("/api/free-tax-transfereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_tax_transfered", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-tax-transfereds : Updates an existing free_tax_transfered.
     *
     * @param free_tax_transfered the free_tax_transfered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_tax_transfered,
     * or with status 400 (Bad Request) if the free_tax_transfered is not valid,
     * or with status 500 (Internal Server Error) if the free_tax_transfered couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-tax-transfereds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_transfered> updateFree_tax_transfered(@Valid @RequestBody Free_tax_transfered free_tax_transfered) throws URISyntaxException {
        log.debug("REST request to update Free_tax_transfered : {}", free_tax_transfered);
        if (free_tax_transfered.getId() == null) {
            return createFree_tax_transfered(free_tax_transfered);
        }
        Free_tax_transfered result = free_tax_transferedService.save(free_tax_transfered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_tax_transfered", free_tax_transfered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-tax-transfereds : get all the free_tax_transfereds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_tax_transfereds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-tax-transfereds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_tax_transfered>> getAllFree_tax_transfereds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_tax_transfereds");
        Page<Free_tax_transfered> page = free_tax_transferedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-tax-transfereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-tax-transfereds/:id : get the "id" free_tax_transfered.
     *
     * @param id the id of the free_tax_transfered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_tax_transfered, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-tax-transfereds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_tax_transfered> getFree_tax_transfered(@PathVariable Long id) {
        log.debug("REST request to get Free_tax_transfered : {}", id);
        Free_tax_transfered free_tax_transfered = free_tax_transferedService.findOne(id);
        return Optional.ofNullable(free_tax_transfered)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-tax-transfereds/:id : delete the "id" free_tax_transfered.
     *
     * @param id the id of the free_tax_transfered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-tax-transfereds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_tax_transfered(@PathVariable Long id) {
        log.debug("REST request to delete Free_tax_transfered : {}", id);
        free_tax_transferedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_tax_transfered", id.toString())).build();
    }

}
