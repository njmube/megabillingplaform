package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_paybill_concept;
import org.megapractical.billingplatform.service.Com_paybill_conceptService;
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
 * REST controller for managing Com_paybill_concept.
 */
@RestController
@RequestMapping("/api")
public class Com_paybill_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Com_paybill_conceptResource.class);
        
    @Inject
    private Com_paybill_conceptService com_paybill_conceptService;
    
    /**
     * POST  /com-paybill-concepts : Create a new com_paybill_concept.
     *
     * @param com_paybill_concept the com_paybill_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_paybill_concept, or with status 400 (Bad Request) if the com_paybill_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-paybill-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_paybill_concept> createCom_paybill_concept(@Valid @RequestBody Com_paybill_concept com_paybill_concept) throws URISyntaxException {
        log.debug("REST request to save Com_paybill_concept : {}", com_paybill_concept);
        if (com_paybill_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_paybill_concept", "idexists", "A new com_paybill_concept cannot already have an ID")).body(null);
        }
        Com_paybill_concept result = com_paybill_conceptService.save(com_paybill_concept);
        return ResponseEntity.created(new URI("/api/com-paybill-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_paybill_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-paybill-concepts : Updates an existing com_paybill_concept.
     *
     * @param com_paybill_concept the com_paybill_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_paybill_concept,
     * or with status 400 (Bad Request) if the com_paybill_concept is not valid,
     * or with status 500 (Internal Server Error) if the com_paybill_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-paybill-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_paybill_concept> updateCom_paybill_concept(@Valid @RequestBody Com_paybill_concept com_paybill_concept) throws URISyntaxException {
        log.debug("REST request to update Com_paybill_concept : {}", com_paybill_concept);
        if (com_paybill_concept.getId() == null) {
            return createCom_paybill_concept(com_paybill_concept);
        }
        Com_paybill_concept result = com_paybill_conceptService.save(com_paybill_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_paybill_concept", com_paybill_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-paybill-concepts : get all the com_paybill_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_paybill_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-paybill-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_paybill_concept>> getAllCom_paybill_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_paybill_concepts");
        Page<Com_paybill_concept> page = com_paybill_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-paybill-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-paybill-concepts/:id : get the "id" com_paybill_concept.
     *
     * @param id the id of the com_paybill_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_paybill_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-paybill-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_paybill_concept> getCom_paybill_concept(@PathVariable Long id) {
        log.debug("REST request to get Com_paybill_concept : {}", id);
        Com_paybill_concept com_paybill_concept = com_paybill_conceptService.findOne(id);
        return Optional.ofNullable(com_paybill_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-paybill-concepts/:id : delete the "id" com_paybill_concept.
     *
     * @param id the id of the com_paybill_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-paybill-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_paybill_concept(@PathVariable Long id) {
        log.debug("REST request to delete Com_paybill_concept : {}", id);
        com_paybill_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_paybill_concept", id.toString())).build();
    }

}
