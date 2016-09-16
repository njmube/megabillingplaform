package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Part_concept;
import org.megapractical.billingplatform.service.Part_conceptService;
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
 * REST controller for managing Part_concept.
 */
@RestController
@RequestMapping("/api")
public class Part_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Part_conceptResource.class);
        
    @Inject
    private Part_conceptService part_conceptService;
    
    /**
     * POST  /part-concepts : Create a new part_concept.
     *
     * @param part_concept the part_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new part_concept, or with status 400 (Bad Request) if the part_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/part-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Part_concept> createPart_concept(@Valid @RequestBody Part_concept part_concept) throws URISyntaxException {
        log.debug("REST request to save Part_concept : {}", part_concept);
        if (part_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("part_concept", "idexists", "A new part_concept cannot already have an ID")).body(null);
        }
        Part_concept result = part_conceptService.save(part_concept);
        return ResponseEntity.created(new URI("/api/part-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("part_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /part-concepts : Updates an existing part_concept.
     *
     * @param part_concept the part_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated part_concept,
     * or with status 400 (Bad Request) if the part_concept is not valid,
     * or with status 500 (Internal Server Error) if the part_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/part-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Part_concept> updatePart_concept(@Valid @RequestBody Part_concept part_concept) throws URISyntaxException {
        log.debug("REST request to update Part_concept : {}", part_concept);
        if (part_concept.getId() == null) {
            return createPart_concept(part_concept);
        }
        Part_concept result = part_conceptService.save(part_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("part_concept", part_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /part-concepts : get all the part_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of part_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/part-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Part_concept>> getAllPart_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Part_concepts");
        Page<Part_concept> page = part_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/part-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /part-concepts/:id : get the "id" part_concept.
     *
     * @param id the id of the part_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the part_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/part-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Part_concept> getPart_concept(@PathVariable Long id) {
        log.debug("REST request to get Part_concept : {}", id);
        Part_concept part_concept = part_conceptService.findOne(id);
        return Optional.ofNullable(part_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /part-concepts/:id : delete the "id" part_concept.
     *
     * @param id the id of the part_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/part-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePart_concept(@PathVariable Long id) {
        log.debug("REST request to delete Part_concept : {}", id);
        part_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("part_concept", id.toString())).build();
    }

}
