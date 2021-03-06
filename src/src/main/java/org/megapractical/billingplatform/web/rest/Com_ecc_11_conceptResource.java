package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_ecc_11_concept;
import org.megapractical.billingplatform.service.Com_ecc_11_conceptService;
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
 * REST controller for managing Com_ecc_11_concept.
 */
@RestController
@RequestMapping("/api")
public class Com_ecc_11_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11_conceptResource.class);
        
    @Inject
    private Com_ecc_11_conceptService com_ecc_11_conceptService;
    
    /**
     * POST  /com-ecc-11-concepts : Create a new com_ecc_11_concept.
     *
     * @param com_ecc_11_concept the com_ecc_11_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_ecc_11_concept, or with status 400 (Bad Request) if the com_ecc_11_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_concept> createCom_ecc_11_concept(@Valid @RequestBody Com_ecc_11_concept com_ecc_11_concept) throws URISyntaxException {
        log.debug("REST request to save Com_ecc_11_concept : {}", com_ecc_11_concept);
        if (com_ecc_11_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_ecc_11_concept", "idexists", "A new com_ecc_11_concept cannot already have an ID")).body(null);
        }
        Com_ecc_11_concept result = com_ecc_11_conceptService.save(com_ecc_11_concept);
        return ResponseEntity.created(new URI("/api/com-ecc-11-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_ecc_11_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-ecc-11-concepts : Updates an existing com_ecc_11_concept.
     *
     * @param com_ecc_11_concept the com_ecc_11_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_ecc_11_concept,
     * or with status 400 (Bad Request) if the com_ecc_11_concept is not valid,
     * or with status 500 (Internal Server Error) if the com_ecc_11_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_concept> updateCom_ecc_11_concept(@Valid @RequestBody Com_ecc_11_concept com_ecc_11_concept) throws URISyntaxException {
        log.debug("REST request to update Com_ecc_11_concept : {}", com_ecc_11_concept);
        if (com_ecc_11_concept.getId() == null) {
            return createCom_ecc_11_concept(com_ecc_11_concept);
        }
        Com_ecc_11_concept result = com_ecc_11_conceptService.save(com_ecc_11_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_ecc_11_concept", com_ecc_11_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-ecc-11-concepts : get all the com_ecc_11_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_ecc_11_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-ecc-11-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_ecc_11_concept>> getAllCom_ecc_11_concepts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_ecc_11_concepts");
        Page<Com_ecc_11_concept> page = com_ecc_11_conceptService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-ecc-11-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-ecc-11-concepts/:id : get the "id" com_ecc_11_concept.
     *
     * @param id the id of the com_ecc_11_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_ecc_11_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-ecc-11-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_concept> getCom_ecc_11_concept(@PathVariable Long id) {
        log.debug("REST request to get Com_ecc_11_concept : {}", id);
        Com_ecc_11_concept com_ecc_11_concept = com_ecc_11_conceptService.findOne(id);
        return Optional.ofNullable(com_ecc_11_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-ecc-11-concepts/:id : delete the "id" com_ecc_11_concept.
     *
     * @param id the id of the com_ecc_11_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-ecc-11-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_ecc_11_concept(@PathVariable Long id) {
        log.debug("REST request to delete Com_ecc_11_concept : {}", id);
        com_ecc_11_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_ecc_11_concept", id.toString())).build();
    }

}
