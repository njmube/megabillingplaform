package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_reciver;
import org.megapractical.billingplatform.service.Free_reciverService;
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
 * REST controller for managing Free_reciver.
 */
@RestController
@RequestMapping("/api")
public class Free_reciverResource {

    private final Logger log = LoggerFactory.getLogger(Free_reciverResource.class);
        
    @Inject
    private Free_reciverService free_reciverService;
    
    /**
     * POST  /free-recivers : Create a new free_reciver.
     *
     * @param free_reciver the free_reciver to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_reciver, or with status 400 (Bad Request) if the free_reciver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-recivers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_reciver> createFree_reciver(@Valid @RequestBody Free_reciver free_reciver) throws URISyntaxException {
        log.debug("REST request to save Free_reciver : {}", free_reciver);
        if (free_reciver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_reciver", "idexists", "A new free_reciver cannot already have an ID")).body(null);
        }
        Free_reciver result = free_reciverService.save(free_reciver);
        return ResponseEntity.created(new URI("/api/free-recivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_reciver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-recivers : Updates an existing free_reciver.
     *
     * @param free_reciver the free_reciver to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_reciver,
     * or with status 400 (Bad Request) if the free_reciver is not valid,
     * or with status 500 (Internal Server Error) if the free_reciver couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-recivers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_reciver> updateFree_reciver(@Valid @RequestBody Free_reciver free_reciver) throws URISyntaxException {
        log.debug("REST request to update Free_reciver : {}", free_reciver);
        if (free_reciver.getId() == null) {
            return createFree_reciver(free_reciver);
        }
        Free_reciver result = free_reciverService.save(free_reciver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_reciver", free_reciver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-recivers : get all the free_recivers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_recivers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-recivers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_reciver>> getAllFree_recivers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_recivers");
        Page<Free_reciver> page = free_reciverService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-recivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-recivers/:id : get the "id" free_reciver.
     *
     * @param id the id of the free_reciver to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_reciver, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-recivers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_reciver> getFree_reciver(@PathVariable Long id) {
        log.debug("REST request to get Free_reciver : {}", id);
        Free_reciver free_reciver = free_reciverService.findOne(id);
        return Optional.ofNullable(free_reciver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-recivers/:id : delete the "id" free_reciver.
     *
     * @param id the id of the free_reciver to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-recivers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_reciver(@PathVariable Long id) {
        log.debug("REST request to delete Free_reciver : {}", id);
        free_reciverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_reciver", id.toString())).build();
    }

}
