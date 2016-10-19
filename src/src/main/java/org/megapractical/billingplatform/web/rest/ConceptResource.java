package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.service.ConceptService;
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
 * REST controller for managing Concept.
 */
@RestController
@RequestMapping("/api")
public class ConceptResource {

    private final Logger log = LoggerFactory.getLogger(ConceptResource.class);
        
    @Inject
    private ConceptService conceptService;
    
    /**
     * POST  /concepts : Create a new concept.
     *
     * @param concept the concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concept, or with status 400 (Bad Request) if the concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> createConcept(@Valid @RequestBody Concept concept) throws URISyntaxException {
        log.debug("REST request to save Concept : {}", concept);
        if (concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("concept", "idexists", "A new concept cannot already have an ID")).body(null);
        }
        Concept result = conceptService.save(concept);
        return ResponseEntity.created(new URI("/api/concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concepts : Updates an existing concept.
     *
     * @param concept the concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concept,
     * or with status 400 (Bad Request) if the concept is not valid,
     * or with status 500 (Internal Server Error) if the concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> updateConcept(@Valid @RequestBody Concept concept) throws URISyntaxException {
        log.debug("REST request to update Concept : {}", concept);
        if (concept.getId() == null) {
            return createConcept(concept);
        }
        Concept result = conceptService.save(concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("concept", concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /concepts : get all the concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Concept>> getAllConcepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Concepts");
        Page<Concept> page = conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concepts/:id : get the "id" concept.
     *
     * @param id the id of the concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        log.debug("REST request to get Concept : {}", id);
        Concept concept = conceptService.findOne(id);
        return Optional.ofNullable(concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /concepts/:id : delete the "id" concept.
     *
     * @param id the id of the concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id) {
        log.debug("REST request to delete Concept : {}", id);
        conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("concept", id.toString())).build();
    }

}
