package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_state_event;
import org.megapractical.billingplatform.service.C_state_eventService;
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
 * REST controller for managing C_state_event.
 */
@RestController
@RequestMapping("/api")
public class C_state_eventResource {

    private final Logger log = LoggerFactory.getLogger(C_state_eventResource.class);
        
    @Inject
    private C_state_eventService c_state_eventService;
    
    /**
     * POST  /c-state-events : Create a new c_state_event.
     *
     * @param c_state_event the c_state_event to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_state_event, or with status 400 (Bad Request) if the c_state_event has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-state-events",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state_event> createC_state_event(@Valid @RequestBody C_state_event c_state_event) throws URISyntaxException {
        log.debug("REST request to save C_state_event : {}", c_state_event);
        if (c_state_event.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_state_event", "idexists", "A new c_state_event cannot already have an ID")).body(null);
        }
        C_state_event result = c_state_eventService.save(c_state_event);
        return ResponseEntity.created(new URI("/api/c-state-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_state_event", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-state-events : Updates an existing c_state_event.
     *
     * @param c_state_event the c_state_event to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_state_event,
     * or with status 400 (Bad Request) if the c_state_event is not valid,
     * or with status 500 (Internal Server Error) if the c_state_event couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-state-events",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state_event> updateC_state_event(@Valid @RequestBody C_state_event c_state_event) throws URISyntaxException {
        log.debug("REST request to update C_state_event : {}", c_state_event);
        if (c_state_event.getId() == null) {
            return createC_state_event(c_state_event);
        }
        C_state_event result = c_state_eventService.save(c_state_event);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_state_event", c_state_event.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-state-events : get all the c_state_events.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_state_events in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-state-events",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_state_event>> getAllC_state_events(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_state_events");
        Page<C_state_event> page = c_state_eventService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-state-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-state-events/:id : get the "id" c_state_event.
     *
     * @param id the id of the c_state_event to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_state_event, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-state-events/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state_event> getC_state_event(@PathVariable Long id) {
        log.debug("REST request to get C_state_event : {}", id);
        C_state_event c_state_event = c_state_eventService.findOne(id);
        return Optional.ofNullable(c_state_event)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-state-events/:id : delete the "id" c_state_event.
     *
     * @param id the id of the c_state_event to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-state-events/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_state_event(@PathVariable Long id) {
        log.debug("REST request to delete C_state_event : {}", id);
        c_state_eventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_state_event", id.toString())).build();
    }

}
