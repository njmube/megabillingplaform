package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_cfdi;
import org.megapractical.billingplatform.service.Free_cfdiService;
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
 * REST controller for managing Free_cfdi.
 */
@RestController
@RequestMapping("/api")
public class Free_cfdiResource {

    private final Logger log = LoggerFactory.getLogger(Free_cfdiResource.class);
        
    @Inject
    private Free_cfdiService free_cfdiService;
    
    /**
     * POST  /free-cfdis : Create a new free_cfdi.
     *
     * @param free_cfdi the free_cfdi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_cfdi, or with status 400 (Bad Request) if the free_cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> createFree_cfdi(@Valid @RequestBody Free_cfdi free_cfdi) throws URISyntaxException {
        log.debug("REST request to save Free_cfdi : {}", free_cfdi);
        if (free_cfdi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_cfdi", "idexists", "A new free_cfdi cannot already have an ID")).body(null);
        }
        Free_cfdi result = free_cfdiService.save(free_cfdi);
        return ResponseEntity.created(new URI("/api/free-cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-cfdis : Updates an existing free_cfdi.
     *
     * @param free_cfdi the free_cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_cfdi,
     * or with status 400 (Bad Request) if the free_cfdi is not valid,
     * or with status 500 (Internal Server Error) if the free_cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> updateFree_cfdi(@Valid @RequestBody Free_cfdi free_cfdi) throws URISyntaxException {
        log.debug("REST request to update Free_cfdi : {}", free_cfdi);
        if (free_cfdi.getId() == null) {
            return createFree_cfdi(free_cfdi);
        }
        Free_cfdi result = free_cfdiService.save(free_cfdi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_cfdi", free_cfdi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-cfdis : get all the free_cfdis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_cfdis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_cfdi>> getAllFree_cfdis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_cfdis");
        Page<Free_cfdi> page = free_cfdiService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-cfdis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-cfdis/:id : get the "id" free_cfdi.
     *
     * @param id the id of the free_cfdi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_cfdi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-cfdis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_cfdi> getFree_cfdi(@PathVariable Long id) {
        log.debug("REST request to get Free_cfdi : {}", id);
        Free_cfdi free_cfdi = free_cfdiService.findOne(id);
        return Optional.ofNullable(free_cfdi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-cfdis/:id : delete the "id" free_cfdi.
     *
     * @param id the id of the free_cfdi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-cfdis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_cfdi(@PathVariable Long id) {
        log.debug("REST request to delete Free_cfdi : {}", id);
        free_cfdiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_cfdi", id.toString())).build();
    }

}
