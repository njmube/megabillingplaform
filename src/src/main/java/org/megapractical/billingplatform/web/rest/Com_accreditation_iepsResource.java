package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_accreditation_ieps;
import org.megapractical.billingplatform.service.Com_accreditation_iepsService;
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
 * REST controller for managing Com_accreditation_ieps.
 */
@RestController
@RequestMapping("/api")
public class Com_accreditation_iepsResource {

    private final Logger log = LoggerFactory.getLogger(Com_accreditation_iepsResource.class);
        
    @Inject
    private Com_accreditation_iepsService com_accreditation_iepsService;
    
    /**
     * POST  /com-accreditation-ieps : Create a new com_accreditation_ieps.
     *
     * @param com_accreditation_ieps the com_accreditation_ieps to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_accreditation_ieps, or with status 400 (Bad Request) if the com_accreditation_ieps has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-accreditation-ieps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accreditation_ieps> createCom_accreditation_ieps(@Valid @RequestBody Com_accreditation_ieps com_accreditation_ieps) throws URISyntaxException {
        log.debug("REST request to save Com_accreditation_ieps : {}", com_accreditation_ieps);
        if (com_accreditation_ieps.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_accreditation_ieps", "idexists", "A new com_accreditation_ieps cannot already have an ID")).body(null);
        }
        Com_accreditation_ieps result = com_accreditation_iepsService.save(com_accreditation_ieps);
        return ResponseEntity.created(new URI("/api/com-accreditation-ieps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_accreditation_ieps", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-accreditation-ieps : Updates an existing com_accreditation_ieps.
     *
     * @param com_accreditation_ieps the com_accreditation_ieps to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_accreditation_ieps,
     * or with status 400 (Bad Request) if the com_accreditation_ieps is not valid,
     * or with status 500 (Internal Server Error) if the com_accreditation_ieps couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-accreditation-ieps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accreditation_ieps> updateCom_accreditation_ieps(@Valid @RequestBody Com_accreditation_ieps com_accreditation_ieps) throws URISyntaxException {
        log.debug("REST request to update Com_accreditation_ieps : {}", com_accreditation_ieps);
        if (com_accreditation_ieps.getId() == null) {
            return createCom_accreditation_ieps(com_accreditation_ieps);
        }
        Com_accreditation_ieps result = com_accreditation_iepsService.save(com_accreditation_ieps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_accreditation_ieps", com_accreditation_ieps.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-accreditation-ieps : get all the com_accreditation_ieps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_accreditation_ieps in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-accreditation-ieps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_accreditation_ieps>> getAllCom_accreditation_ieps(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_accreditation_ieps");
        Page<Com_accreditation_ieps> page = com_accreditation_iepsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-accreditation-ieps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-accreditation-ieps/:id : get the "id" com_accreditation_ieps.
     *
     * @param id the id of the com_accreditation_ieps to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_accreditation_ieps, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-accreditation-ieps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_accreditation_ieps> getCom_accreditation_ieps(@PathVariable Long id) {
        log.debug("REST request to get Com_accreditation_ieps : {}", id);
        Com_accreditation_ieps com_accreditation_ieps = com_accreditation_iepsService.findOne(id);
        return Optional.ofNullable(com_accreditation_ieps)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-accreditation-ieps/:id : delete the "id" com_accreditation_ieps.
     *
     * @param id the id of the com_accreditation_ieps to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-accreditation-ieps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_accreditation_ieps(@PathVariable Long id) {
        log.debug("REST request to delete Com_accreditation_ieps : {}", id);
        com_accreditation_iepsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_accreditation_ieps", id.toString())).build();
    }

}
