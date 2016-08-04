package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_paybill_concept;
import org.megapractical.billingplatform.service.Freecom_paybill_conceptService;
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
 * REST controller for managing Freecom_paybill_concept.
 */
@RestController
@RequestMapping("/api")
public class Freecom_paybill_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_paybill_conceptResource.class);
        
    @Inject
    private Freecom_paybill_conceptService freecom_paybill_conceptService;
    
    /**
     * POST  /freecom-paybill-concepts : Create a new freecom_paybill_concept.
     *
     * @param freecom_paybill_concept the freecom_paybill_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_paybill_concept, or with status 400 (Bad Request) if the freecom_paybill_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-paybill-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_paybill_concept> createFreecom_paybill_concept(@Valid @RequestBody Freecom_paybill_concept freecom_paybill_concept) throws URISyntaxException {
        log.debug("REST request to save Freecom_paybill_concept : {}", freecom_paybill_concept);
        if (freecom_paybill_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_paybill_concept", "idexists", "A new freecom_paybill_concept cannot already have an ID")).body(null);
        }
        Freecom_paybill_concept result = freecom_paybill_conceptService.save(freecom_paybill_concept);
        return ResponseEntity.created(new URI("/api/freecom-paybill-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_paybill_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-paybill-concepts : Updates an existing freecom_paybill_concept.
     *
     * @param freecom_paybill_concept the freecom_paybill_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_paybill_concept,
     * or with status 400 (Bad Request) if the freecom_paybill_concept is not valid,
     * or with status 500 (Internal Server Error) if the freecom_paybill_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-paybill-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_paybill_concept> updateFreecom_paybill_concept(@Valid @RequestBody Freecom_paybill_concept freecom_paybill_concept) throws URISyntaxException {
        log.debug("REST request to update Freecom_paybill_concept : {}", freecom_paybill_concept);
        if (freecom_paybill_concept.getId() == null) {
            return createFreecom_paybill_concept(freecom_paybill_concept);
        }
        Freecom_paybill_concept result = freecom_paybill_conceptService.save(freecom_paybill_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_paybill_concept", freecom_paybill_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-paybill-concepts : get all the freecom_paybill_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_paybill_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-paybill-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_paybill_concept>> getAllFreecom_paybill_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_paybill_concepts");
        Page<Freecom_paybill_concept> page = freecom_paybill_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-paybill-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-paybill-concepts/:id : get the "id" freecom_paybill_concept.
     *
     * @param id the id of the freecom_paybill_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_paybill_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-paybill-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_paybill_concept> getFreecom_paybill_concept(@PathVariable Long id) {
        log.debug("REST request to get Freecom_paybill_concept : {}", id);
        Freecom_paybill_concept freecom_paybill_concept = freecom_paybill_conceptService.findOne(id);
        return Optional.ofNullable(freecom_paybill_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-paybill-concepts/:id : delete the "id" freecom_paybill_concept.
     *
     * @param id the id of the freecom_paybill_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-paybill-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_paybill_concept(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_paybill_concept : {}", id);
        freecom_paybill_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_paybill_concept", id.toString())).build();
    }

}
