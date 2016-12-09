package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_public_notaries;
import org.megapractical.billingplatform.service.Com_public_notariesService;
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
 * REST controller for managing Com_public_notaries.
 */
@RestController
@RequestMapping("/api")
public class Com_public_notariesResource {

    private final Logger log = LoggerFactory.getLogger(Com_public_notariesResource.class);
        
    @Inject
    private Com_public_notariesService com_public_notariesService;
    
    /**
     * POST  /com-public-notaries : Create a new com_public_notaries.
     *
     * @param com_public_notaries the com_public_notaries to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_public_notaries, or with status 400 (Bad Request) if the com_public_notaries has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-public-notaries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_public_notaries> createCom_public_notaries(@Valid @RequestBody Com_public_notaries com_public_notaries) throws URISyntaxException {
        log.debug("REST request to save Com_public_notaries : {}", com_public_notaries);
        if (com_public_notaries.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_public_notaries", "idexists", "A new com_public_notaries cannot already have an ID")).body(null);
        }
        Com_public_notaries result = com_public_notariesService.save(com_public_notaries);
        return ResponseEntity.created(new URI("/api/com-public-notaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_public_notaries", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-public-notaries : Updates an existing com_public_notaries.
     *
     * @param com_public_notaries the com_public_notaries to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_public_notaries,
     * or with status 400 (Bad Request) if the com_public_notaries is not valid,
     * or with status 500 (Internal Server Error) if the com_public_notaries couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-public-notaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_public_notaries> updateCom_public_notaries(@Valid @RequestBody Com_public_notaries com_public_notaries) throws URISyntaxException {
        log.debug("REST request to update Com_public_notaries : {}", com_public_notaries);
        if (com_public_notaries.getId() == null) {
            return createCom_public_notaries(com_public_notaries);
        }
        Com_public_notaries result = com_public_notariesService.save(com_public_notaries);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_public_notaries", com_public_notaries.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-public-notaries : get all the com_public_notaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_public_notaries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-public-notaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_public_notaries>> getAllCom_public_notaries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_public_notaries");
        Page<Com_public_notaries> page = com_public_notariesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-public-notaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-public-notaries/:id : get the "id" com_public_notaries.
     *
     * @param id the id of the com_public_notaries to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_public_notaries, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-public-notaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_public_notaries> getCom_public_notaries(@PathVariable Long id) {
        log.debug("REST request to get Com_public_notaries : {}", id);
        Com_public_notaries com_public_notaries = com_public_notariesService.findOne(id);
        return Optional.ofNullable(com_public_notaries)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-public-notaries/:id : delete the "id" com_public_notaries.
     *
     * @param id the id of the com_public_notaries to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-public-notaries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_public_notaries(@PathVariable Long id) {
        log.debug("REST request to delete Com_public_notaries : {}", id);
        com_public_notariesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_public_notaries", id.toString())).build();
    }

}
