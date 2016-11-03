package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_local_retentions;
import org.megapractical.billingplatform.service.Com_local_retentionsService;
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
 * REST controller for managing Com_local_retentions.
 */
@RestController
@RequestMapping("/api")
public class Com_local_retentionsResource {

    private final Logger log = LoggerFactory.getLogger(Com_local_retentionsResource.class);
        
    @Inject
    private Com_local_retentionsService com_local_retentionsService;
    
    /**
     * POST  /com-local-retentions : Create a new com_local_retentions.
     *
     * @param com_local_retentions the com_local_retentions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_local_retentions, or with status 400 (Bad Request) if the com_local_retentions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-retentions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_retentions> createCom_local_retentions(@Valid @RequestBody Com_local_retentions com_local_retentions) throws URISyntaxException {
        log.debug("REST request to save Com_local_retentions : {}", com_local_retentions);
        if (com_local_retentions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_local_retentions", "idexists", "A new com_local_retentions cannot already have an ID")).body(null);
        }
        Com_local_retentions result = com_local_retentionsService.save(com_local_retentions);
        return ResponseEntity.created(new URI("/api/com-local-retentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_local_retentions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-local-retentions : Updates an existing com_local_retentions.
     *
     * @param com_local_retentions the com_local_retentions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_local_retentions,
     * or with status 400 (Bad Request) if the com_local_retentions is not valid,
     * or with status 500 (Internal Server Error) if the com_local_retentions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-retentions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_retentions> updateCom_local_retentions(@Valid @RequestBody Com_local_retentions com_local_retentions) throws URISyntaxException {
        log.debug("REST request to update Com_local_retentions : {}", com_local_retentions);
        if (com_local_retentions.getId() == null) {
            return createCom_local_retentions(com_local_retentions);
        }
        Com_local_retentions result = com_local_retentionsService.save(com_local_retentions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_local_retentions", com_local_retentions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-local-retentions : get all the com_local_retentions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_local_retentions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-local-retentions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_local_retentions>> getAllCom_local_retentions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_local_retentions");
        Page<Com_local_retentions> page = com_local_retentionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-local-retentions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-local-retentions/:id : get the "id" com_local_retentions.
     *
     * @param id the id of the com_local_retentions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_local_retentions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-local-retentions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_retentions> getCom_local_retentions(@PathVariable Long id) {
        log.debug("REST request to get Com_local_retentions : {}", id);
        Com_local_retentions com_local_retentions = com_local_retentionsService.findOne(id);
        return Optional.ofNullable(com_local_retentions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-local-retentions/:id : delete the "id" com_local_retentions.
     *
     * @param id the id of the com_local_retentions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-local-retentions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_local_retentions(@PathVariable Long id) {
        log.debug("REST request to delete Com_local_retentions : {}", id);
        com_local_retentionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_local_retentions", id.toString())).build();
    }

}
