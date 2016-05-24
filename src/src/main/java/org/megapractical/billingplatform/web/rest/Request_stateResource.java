package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Request_state;
import org.megapractical.billingplatform.service.Request_stateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Request_state.
 */
@RestController
@RequestMapping("/api")
public class Request_stateResource {

    private final Logger log = LoggerFactory.getLogger(Request_stateResource.class);
        
    @Inject
    private Request_stateService request_stateService;
    
    /**
     * POST  /request-states : Create a new request_state.
     *
     * @param request_state the request_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new request_state, or with status 400 (Bad Request) if the request_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/request-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_state> createRequest_state(@RequestBody Request_state request_state) throws URISyntaxException {
        log.debug("REST request to save Request_state : {}", request_state);
        if (request_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("request_state", "idexists", "A new request_state cannot already have an ID")).body(null);
        }
        Request_state result = request_stateService.save(request_state);
        return ResponseEntity.created(new URI("/api/request-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("request_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /request-states : Updates an existing request_state.
     *
     * @param request_state the request_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated request_state,
     * or with status 400 (Bad Request) if the request_state is not valid,
     * or with status 500 (Internal Server Error) if the request_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/request-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_state> updateRequest_state(@RequestBody Request_state request_state) throws URISyntaxException {
        log.debug("REST request to update Request_state : {}", request_state);
        if (request_state.getId() == null) {
            return createRequest_state(request_state);
        }
        Request_state result = request_stateService.save(request_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("request_state", request_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /request-states : get all the request_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of request_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/request-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Request_state>> getAllRequest_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Request_states");
        Page<Request_state> page = request_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/request-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /request-states/:id : get the "id" request_state.
     *
     * @param id the id of the request_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the request_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/request-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Request_state> getRequest_state(@PathVariable Long id) {
        log.debug("REST request to get Request_state : {}", id);
        Request_state request_state = request_stateService.findOne(id);
        return Optional.ofNullable(request_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /request-states/:id : delete the "id" request_state.
     *
     * @param id the id of the request_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/request-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRequest_state(@PathVariable Long id) {
        log.debug("REST request to delete Request_state : {}", id);
        request_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("request_state", id.toString())).build();
    }

}
