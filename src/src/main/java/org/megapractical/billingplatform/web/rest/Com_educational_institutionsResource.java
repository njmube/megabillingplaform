package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_educational_institutions;
import org.megapractical.billingplatform.service.Com_educational_institutionsService;
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
 * REST controller for managing Com_educational_institutions.
 */
@RestController
@RequestMapping("/api")
public class Com_educational_institutionsResource {

    private final Logger log = LoggerFactory.getLogger(Com_educational_institutionsResource.class);
        
    @Inject
    private Com_educational_institutionsService com_educational_institutionsService;
    
    /**
     * POST  /com-educational-institutions : Create a new com_educational_institutions.
     *
     * @param com_educational_institutions the com_educational_institutions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_educational_institutions, or with status 400 (Bad Request) if the com_educational_institutions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-educational-institutions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_educational_institutions> createCom_educational_institutions(@Valid @RequestBody Com_educational_institutions com_educational_institutions) throws URISyntaxException {
        log.debug("REST request to save Com_educational_institutions : {}", com_educational_institutions);
        if (com_educational_institutions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_educational_institutions", "idexists", "A new com_educational_institutions cannot already have an ID")).body(null);
        }
        Com_educational_institutions result = com_educational_institutionsService.save(com_educational_institutions);
        return ResponseEntity.created(new URI("/api/com-educational-institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_educational_institutions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-educational-institutions : Updates an existing com_educational_institutions.
     *
     * @param com_educational_institutions the com_educational_institutions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_educational_institutions,
     * or with status 400 (Bad Request) if the com_educational_institutions is not valid,
     * or with status 500 (Internal Server Error) if the com_educational_institutions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-educational-institutions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_educational_institutions> updateCom_educational_institutions(@Valid @RequestBody Com_educational_institutions com_educational_institutions) throws URISyntaxException {
        log.debug("REST request to update Com_educational_institutions : {}", com_educational_institutions);
        if (com_educational_institutions.getId() == null) {
            return createCom_educational_institutions(com_educational_institutions);
        }
        Com_educational_institutions result = com_educational_institutionsService.save(com_educational_institutions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_educational_institutions", com_educational_institutions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-educational-institutions : get all the com_educational_institutions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_educational_institutions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-educational-institutions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_educational_institutions>> getAllCom_educational_institutions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_educational_institutions");
        Page<Com_educational_institutions> page = com_educational_institutionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-educational-institutions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-educational-institutions/:id : get the "id" com_educational_institutions.
     *
     * @param id the id of the com_educational_institutions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_educational_institutions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-educational-institutions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_educational_institutions> getCom_educational_institutions(@PathVariable Long id) {
        log.debug("REST request to get Com_educational_institutions : {}", id);
        Com_educational_institutions com_educational_institutions = com_educational_institutionsService.findOne(id);
        return Optional.ofNullable(com_educational_institutions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-educational-institutions/:id : delete the "id" com_educational_institutions.
     *
     * @param id the id of the com_educational_institutions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-educational-institutions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_educational_institutions(@PathVariable Long id) {
        log.debug("REST request to delete Com_educational_institutions : {}", id);
        com_educational_institutionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_educational_institutions", id.toString())).build();
    }

}
