package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Free_receiver;
import org.megapractical.billingplatform.service.Free_receiverService;
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
 * REST controller for managing Free_receiver.
 */
@RestController
@RequestMapping("/api")
public class Free_receiverResource {

    private final Logger log = LoggerFactory.getLogger(Free_receiverResource.class);
        
    @Inject
    private Free_receiverService free_receiverService;
    
    /**
     * POST  /free-receivers : Create a new free_receiver.
     *
     * @param free_receiver the free_receiver to create
     * @return the ResponseEntity with status 201 (Created) and with body the new free_receiver, or with status 400 (Bad Request) if the free_receiver has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-receivers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_receiver> createFree_receiver(@Valid @RequestBody Free_receiver free_receiver) throws URISyntaxException {
        log.debug("REST request to save Free_receiver : {}", free_receiver);
        if (free_receiver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("free_receiver", "idexists", "A new free_receiver cannot already have an ID")).body(null);
        }
        Free_receiver result = free_receiverService.save(free_receiver);
        return ResponseEntity.created(new URI("/api/free-receivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("free_receiver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /free-receivers : Updates an existing free_receiver.
     *
     * @param free_receiver the free_receiver to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated free_receiver,
     * or with status 400 (Bad Request) if the free_receiver is not valid,
     * or with status 500 (Internal Server Error) if the free_receiver couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/free-receivers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_receiver> updateFree_receiver(@Valid @RequestBody Free_receiver free_receiver) throws URISyntaxException {
        log.debug("REST request to update Free_receiver : {}", free_receiver);
        if (free_receiver.getId() == null) {
            return createFree_receiver(free_receiver);
        }
        Free_receiver result = free_receiverService.save(free_receiver);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("free_receiver", free_receiver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /free-receivers : get all the free_receivers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of free_receivers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/free-receivers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Free_receiver>> getAllFree_receivers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Free_receivers");
        Page<Free_receiver> page = free_receiverService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/free-receivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /free-receivers/:id : get the "id" free_receiver.
     *
     * @param id the id of the free_receiver to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the free_receiver, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/free-receivers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Free_receiver> getFree_receiver(@PathVariable Long id) {
        log.debug("REST request to get Free_receiver : {}", id);
        Free_receiver free_receiver = free_receiverService.findOne(id);
        return Optional.ofNullable(free_receiver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /free-receivers/:id : delete the "id" free_receiver.
     *
     * @param id the id of the free_receiver to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/free-receivers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFree_receiver(@PathVariable Long id) {
        log.debug("REST request to delete Free_receiver : {}", id);
        free_receiverService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("free_receiver", id.toString())).build();
    }

}
