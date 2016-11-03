package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_local_transfered;
import org.megapractical.billingplatform.service.Com_local_transferedService;
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
 * REST controller for managing Com_local_transfered.
 */
@RestController
@RequestMapping("/api")
public class Com_local_transferedResource {

    private final Logger log = LoggerFactory.getLogger(Com_local_transferedResource.class);
        
    @Inject
    private Com_local_transferedService com_local_transferedService;
    
    /**
     * POST  /com-local-transfereds : Create a new com_local_transfered.
     *
     * @param com_local_transfered the com_local_transfered to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_local_transfered, or with status 400 (Bad Request) if the com_local_transfered has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-transfereds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_transfered> createCom_local_transfered(@Valid @RequestBody Com_local_transfered com_local_transfered) throws URISyntaxException {
        log.debug("REST request to save Com_local_transfered : {}", com_local_transfered);
        if (com_local_transfered.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_local_transfered", "idexists", "A new com_local_transfered cannot already have an ID")).body(null);
        }
        Com_local_transfered result = com_local_transferedService.save(com_local_transfered);
        return ResponseEntity.created(new URI("/api/com-local-transfereds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_local_transfered", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-local-transfereds : Updates an existing com_local_transfered.
     *
     * @param com_local_transfered the com_local_transfered to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_local_transfered,
     * or with status 400 (Bad Request) if the com_local_transfered is not valid,
     * or with status 500 (Internal Server Error) if the com_local_transfered couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-transfereds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_transfered> updateCom_local_transfered(@Valid @RequestBody Com_local_transfered com_local_transfered) throws URISyntaxException {
        log.debug("REST request to update Com_local_transfered : {}", com_local_transfered);
        if (com_local_transfered.getId() == null) {
            return createCom_local_transfered(com_local_transfered);
        }
        Com_local_transfered result = com_local_transferedService.save(com_local_transfered);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_local_transfered", com_local_transfered.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-local-transfereds : get all the com_local_transfereds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_local_transfereds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-local-transfereds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_local_transfered>> getAllCom_local_transfereds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_local_transfereds");
        Page<Com_local_transfered> page = com_local_transferedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-local-transfereds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-local-transfereds/:id : get the "id" com_local_transfered.
     *
     * @param id the id of the com_local_transfered to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_local_transfered, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-local-transfereds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_transfered> getCom_local_transfered(@PathVariable Long id) {
        log.debug("REST request to get Com_local_transfered : {}", id);
        Com_local_transfered com_local_transfered = com_local_transferedService.findOne(id);
        return Optional.ofNullable(com_local_transfered)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-local-transfereds/:id : delete the "id" com_local_transfered.
     *
     * @param id the id of the com_local_transfered to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-local-transfereds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_local_transfered(@PathVariable Long id) {
        log.debug("REST request to delete Com_local_transfered : {}", id);
        com_local_transferedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_local_transfered", id.toString())).build();
    }

}
