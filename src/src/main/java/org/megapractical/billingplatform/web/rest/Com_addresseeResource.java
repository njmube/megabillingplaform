package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_addressee;
import org.megapractical.billingplatform.service.Com_addresseeService;
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
 * REST controller for managing Com_addressee.
 */
@RestController
@RequestMapping("/api")
public class Com_addresseeResource {

    private final Logger log = LoggerFactory.getLogger(Com_addresseeResource.class);
        
    @Inject
    private Com_addresseeService com_addresseeService;
    
    /**
     * POST  /com-addressees : Create a new com_addressee.
     *
     * @param com_addressee the com_addressee to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_addressee, or with status 400 (Bad Request) if the com_addressee has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-addressees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_addressee> createCom_addressee(@Valid @RequestBody Com_addressee com_addressee) throws URISyntaxException {
        log.debug("REST request to save Com_addressee : {}", com_addressee);
        if (com_addressee.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_addressee", "idexists", "A new com_addressee cannot already have an ID")).body(null);
        }
        Com_addressee result = com_addresseeService.save(com_addressee);
        return ResponseEntity.created(new URI("/api/com-addressees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_addressee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-addressees : Updates an existing com_addressee.
     *
     * @param com_addressee the com_addressee to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_addressee,
     * or with status 400 (Bad Request) if the com_addressee is not valid,
     * or with status 500 (Internal Server Error) if the com_addressee couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-addressees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_addressee> updateCom_addressee(@Valid @RequestBody Com_addressee com_addressee) throws URISyntaxException {
        log.debug("REST request to update Com_addressee : {}", com_addressee);
        if (com_addressee.getId() == null) {
            return createCom_addressee(com_addressee);
        }
        Com_addressee result = com_addresseeService.save(com_addressee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_addressee", com_addressee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-addressees : get all the com_addressees.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_addressees in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-addressees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_addressee>> getAllCom_addressees(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_addressees");
        Page<Com_addressee> page = com_addresseeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-addressees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-addressees/:id : get the "id" com_addressee.
     *
     * @param id the id of the com_addressee to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_addressee, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-addressees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_addressee> getCom_addressee(@PathVariable Long id) {
        log.debug("REST request to get Com_addressee : {}", id);
        Com_addressee com_addressee = com_addresseeService.findOne(id);
        return Optional.ofNullable(com_addressee)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-addressees/:id : delete the "id" com_addressee.
     *
     * @param id the id of the com_addressee to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-addressees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_addressee(@PathVariable Long id) {
        log.debug("REST request to delete Com_addressee : {}", id);
        com_addresseeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_addressee", id.toString())).build();
    }

}
