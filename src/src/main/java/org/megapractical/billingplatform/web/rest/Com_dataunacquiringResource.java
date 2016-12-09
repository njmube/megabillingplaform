package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_dataunacquiring;
import org.megapractical.billingplatform.service.Com_dataunacquiringService;
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
 * REST controller for managing Com_dataunacquiring.
 */
@RestController
@RequestMapping("/api")
public class Com_dataunacquiringResource {

    private final Logger log = LoggerFactory.getLogger(Com_dataunacquiringResource.class);
        
    @Inject
    private Com_dataunacquiringService com_dataunacquiringService;
    
    /**
     * POST  /com-dataunacquirings : Create a new com_dataunacquiring.
     *
     * @param com_dataunacquiring the com_dataunacquiring to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_dataunacquiring, or with status 400 (Bad Request) if the com_dataunacquiring has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataunacquirings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunacquiring> createCom_dataunacquiring(@Valid @RequestBody Com_dataunacquiring com_dataunacquiring) throws URISyntaxException {
        log.debug("REST request to save Com_dataunacquiring : {}", com_dataunacquiring);
        if (com_dataunacquiring.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_dataunacquiring", "idexists", "A new com_dataunacquiring cannot already have an ID")).body(null);
        }
        Com_dataunacquiring result = com_dataunacquiringService.save(com_dataunacquiring);
        return ResponseEntity.created(new URI("/api/com-dataunacquirings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_dataunacquiring", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-dataunacquirings : Updates an existing com_dataunacquiring.
     *
     * @param com_dataunacquiring the com_dataunacquiring to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_dataunacquiring,
     * or with status 400 (Bad Request) if the com_dataunacquiring is not valid,
     * or with status 500 (Internal Server Error) if the com_dataunacquiring couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-dataunacquirings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunacquiring> updateCom_dataunacquiring(@Valid @RequestBody Com_dataunacquiring com_dataunacquiring) throws URISyntaxException {
        log.debug("REST request to update Com_dataunacquiring : {}", com_dataunacquiring);
        if (com_dataunacquiring.getId() == null) {
            return createCom_dataunacquiring(com_dataunacquiring);
        }
        Com_dataunacquiring result = com_dataunacquiringService.save(com_dataunacquiring);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_dataunacquiring", com_dataunacquiring.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-dataunacquirings : get all the com_dataunacquirings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_dataunacquirings in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-dataunacquirings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_dataunacquiring>> getAllCom_dataunacquirings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_dataunacquirings");
        Page<Com_dataunacquiring> page = com_dataunacquiringService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-dataunacquirings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-dataunacquirings/:id : get the "id" com_dataunacquiring.
     *
     * @param id the id of the com_dataunacquiring to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_dataunacquiring, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-dataunacquirings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_dataunacquiring> getCom_dataunacquiring(@PathVariable Long id) {
        log.debug("REST request to get Com_dataunacquiring : {}", id);
        Com_dataunacquiring com_dataunacquiring = com_dataunacquiringService.findOne(id);
        return Optional.ofNullable(com_dataunacquiring)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-dataunacquirings/:id : delete the "id" com_dataunacquiring.
     *
     * @param id the id of the com_dataunacquiring to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-dataunacquirings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_dataunacquiring(@PathVariable Long id) {
        log.debug("REST request to delete Com_dataunacquiring : {}", id);
        com_dataunacquiringService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_dataunacquiring", id.toString())).build();
    }

}
