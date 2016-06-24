package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_concept;
import org.megapractical.billingplatform.service.Free_conceptService;
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
 * REST controller for managing Free_concept.
 */
@RestController
@RequestMapping("/api")
public class Free_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Free_conceptResource.class);
        
    @Inject
    private Free_conceptService free_conceptService;
    
    /**
     * POST  /free-concepts : Create a new free_concept.
     *
     * @param free_concept the free_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_concept, or with status 400 (Bad Request) if the free_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_concept> createFree_concept(@Valid @RequestBody Free_concept free_concept) throws URISyntaxException {
        log.debug("REST request to save Free_concept : {}", free_concept);
        if (free_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_concept", "idexists", "A new free_concept cannot already have an ID")).body(null);
        }
        Free_concept result = free_conceptService.save(free_concept);
        return ResponseEntity.created(new URI("/api/free-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-concepts : Updates an existing free_concept.
     *
     * @param free_concept the free_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_concept,
     * or with status 400 (Bad Request) if the free_concept is not valid,
     * or with status 500 (Internal Server Error) if the free_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_concept> updateFree_concept(@Valid @RequestBody Free_concept free_concept) throws URISyntaxException {
        log.debug("REST request to update Free_concept : {}", free_concept);
        if (free_concept.getId() == null) {
            return createFree_concept(free_concept);
        }
        Free_concept result = free_conceptService.save(free_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_concept", free_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-concepts : get all the free_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_concept>> getAllFree_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_concepts");
        Page<Free_concept> page = free_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-concepts/:id : get the "id" free_concept.
     *
     * @param id the id of the free_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_concept> getFree_concept(@PathVariable Long id) {
        log.debug("REST request to get Free_concept : {}", id);
        Free_concept free_concept = free_conceptService.findOne(id);
        return Optional.ofNullable(free_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-concepts/:id : delete the "id" free_concept.
     *
     * @param id the id of the free_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_concept(@PathVariable Long id) {
        log.debug("REST request to delete Free_concept : {}", id);
        free_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_concept", id.toString())).build();
    }

}
