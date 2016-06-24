package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_part_concept;
import org.megapractical.billingplatform.service.Free_part_conceptService;
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
 * REST controller for managing Free_part_concept.
 */
@RestController
@RequestMapping("/api")
public class Free_part_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Free_part_conceptResource.class);
        
    @Inject
    private Free_part_conceptService free_part_conceptService;
    
    /**
     * POST  /free-part-concepts : Create a new free_part_concept.
     *
     * @param free_part_concept the free_part_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_part_concept, or with status 400 (Bad Request) if the free_part_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-part-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_part_concept> createFree_part_concept(@Valid @RequestBody Free_part_concept free_part_concept) throws URISyntaxException {
        log.debug("REST request to save Free_part_concept : {}", free_part_concept);
        if (free_part_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_part_concept", "idexists", "A new free_part_concept cannot already have an ID")).body(null);
        }
        Free_part_concept result = free_part_conceptService.save(free_part_concept);
        return ResponseEntity.created(new URI("/api/free-part-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_part_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-part-concepts : Updates an existing free_part_concept.
     *
     * @param free_part_concept the free_part_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_part_concept,
     * or with status 400 (Bad Request) if the free_part_concept is not valid,
     * or with status 500 (Internal Server Error) if the free_part_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-part-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_part_concept> updateFree_part_concept(@Valid @RequestBody Free_part_concept free_part_concept) throws URISyntaxException {
        log.debug("REST request to update Free_part_concept : {}", free_part_concept);
        if (free_part_concept.getId() == null) {
            return createFree_part_concept(free_part_concept);
        }
        Free_part_concept result = free_part_conceptService.save(free_part_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_part_concept", free_part_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-part-concepts : get all the free_part_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_part_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-part-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_part_concept>> getAllFree_part_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_part_concepts");
        Page<Free_part_concept> page = free_part_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-part-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-part-concepts/:id : get the "id" free_part_concept.
     *
     * @param id the id of the free_part_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_part_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-part-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_part_concept> getFree_part_concept(@PathVariable Long id) {
        log.debug("REST request to get Free_part_concept : {}", id);
        Free_part_concept free_part_concept = free_part_conceptService.findOne(id);
        return Optional.ofNullable(free_part_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-part-concepts/:id : delete the "id" free_part_concept.
     *
     * @param id the id of the free_part_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-part-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_part_concept(@PathVariable Long id) {
        log.debug("REST request to delete Free_part_concept : {}", id);
        free_part_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_part_concept", id.toString())).build();
    }

}
