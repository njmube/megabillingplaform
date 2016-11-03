package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_donees;
import org.megapractical.billingplatform.service.Com_doneesService;
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
 * REST controller for managing Com_donees.
 */
@RestController
@RequestMapping("/api")
public class Com_doneesResource {

    private final Logger log = LoggerFactory.getLogger(Com_doneesResource.class);
        
    @Inject
    private Com_doneesService com_doneesService;
    
    /**
     * POST  /com-donees : Create a new com_donees.
     *
     * @param com_donees the com_donees to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_donees, or with status 400 (Bad Request) if the com_donees has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-donees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_donees> createCom_donees(@Valid @RequestBody Com_donees com_donees) throws URISyntaxException {
        log.debug("REST request to save Com_donees : {}", com_donees);
        if (com_donees.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_donees", "idexists", "A new com_donees cannot already have an ID")).body(null);
        }
        Com_donees result = com_doneesService.save(com_donees);
        return ResponseEntity.created(new URI("/api/com-donees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_donees", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-donees : Updates an existing com_donees.
     *
     * @param com_donees the com_donees to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_donees,
     * or with status 400 (Bad Request) if the com_donees is not valid,
     * or with status 500 (Internal Server Error) if the com_donees couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-donees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_donees> updateCom_donees(@Valid @RequestBody Com_donees com_donees) throws URISyntaxException {
        log.debug("REST request to update Com_donees : {}", com_donees);
        if (com_donees.getId() == null) {
            return createCom_donees(com_donees);
        }
        Com_donees result = com_doneesService.save(com_donees);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_donees", com_donees.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-donees : get all the com_donees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_donees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-donees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_donees>> getAllCom_donees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_donees");
        Page<Com_donees> page = com_doneesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-donees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-donees/:id : get the "id" com_donees.
     *
     * @param id the id of the com_donees to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_donees, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-donees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_donees> getCom_donees(@PathVariable Long id) {
        log.debug("REST request to get Com_donees : {}", id);
        Com_donees com_donees = com_doneesService.findOne(id);
        return Optional.ofNullable(com_donees)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-donees/:id : delete the "id" com_donees.
     *
     * @param id the id of the com_donees to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-donees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_donees(@PathVariable Long id) {
        log.debug("REST request to delete Com_donees : {}", id);
        com_doneesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_donees", id.toString())).build();
    }

}
