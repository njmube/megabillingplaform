package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_ecc11_concept;
import org.megapractical.billingplatform.service.Freecom_ecc11_conceptService;
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
 * REST controller for managing Freecom_ecc11_concept.
 */
@RestController
@RequestMapping("/api")
public class Freecom_ecc11_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11_conceptResource.class);
        
    @Inject
    private Freecom_ecc11_conceptService freecom_ecc11_conceptService;
    
    /**
     * POST  /freecom-ecc-11-concepts : Create a new freecom_ecc11_concept.
     *
     * @param freecom_ecc11_concept the freecom_ecc11_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_ecc11_concept, or with status 400 (Bad Request) if the freecom_ecc11_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_concept> createFreecom_ecc11_concept(@Valid @RequestBody Freecom_ecc11_concept freecom_ecc11_concept) throws URISyntaxException {
        log.debug("REST request to save Freecom_ecc11_concept : {}", freecom_ecc11_concept);
        if (freecom_ecc11_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_ecc11_concept", "idexists", "A new freecom_ecc11_concept cannot already have an ID")).body(null);
        }
        Freecom_ecc11_concept result = freecom_ecc11_conceptService.save(freecom_ecc11_concept);
        return ResponseEntity.created(new URI("/api/freecom-ecc-11-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_ecc11_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-ecc-11-concepts : Updates an existing freecom_ecc11_concept.
     *
     * @param freecom_ecc11_concept the freecom_ecc11_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_ecc11_concept,
     * or with status 400 (Bad Request) if the freecom_ecc11_concept is not valid,
     * or with status 500 (Internal Server Error) if the freecom_ecc11_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_concept> updateFreecom_ecc11_concept(@Valid @RequestBody Freecom_ecc11_concept freecom_ecc11_concept) throws URISyntaxException {
        log.debug("REST request to update Freecom_ecc11_concept : {}", freecom_ecc11_concept);
        if (freecom_ecc11_concept.getId() == null) {
            return createFreecom_ecc11_concept(freecom_ecc11_concept);
        }
        Freecom_ecc11_concept result = freecom_ecc11_conceptService.save(freecom_ecc11_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_ecc11_concept", freecom_ecc11_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-ecc-11-concepts : get all the freecom_ecc11_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_ecc11_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-ecc-11-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_ecc11_concept>> getAllFreecom_ecc11_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_ecc11_concepts");
        Page<Freecom_ecc11_concept> page = freecom_ecc11_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-ecc-11-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-ecc-11-concepts/:id : get the "id" freecom_ecc11_concept.
     *
     * @param id the id of the freecom_ecc11_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_ecc11_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-ecc-11-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_concept> getFreecom_ecc11_concept(@PathVariable Long id) {
        log.debug("REST request to get Freecom_ecc11_concept : {}", id);
        Freecom_ecc11_concept freecom_ecc11_concept = freecom_ecc11_conceptService.findOne(id);
        return Optional.ofNullable(freecom_ecc11_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-ecc-11-concepts/:id : delete the "id" freecom_ecc11_concept.
     *
     * @param id the id of the freecom_ecc11_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-ecc-11-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_ecc11_concept(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_ecc11_concept : {}", id);
        freecom_ecc11_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_ecc11_concept", id.toString())).build();
    }

}
