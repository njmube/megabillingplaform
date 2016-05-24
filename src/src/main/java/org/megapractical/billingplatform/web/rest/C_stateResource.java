package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_state;
import org.megapractical.billingplatform.service.C_stateService;
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
 * REST controller for managing C_state.
 */
@RestController
@RequestMapping("/api")
public class C_stateResource {

    private final Logger log = LoggerFactory.getLogger(C_stateResource.class);
        
    @Inject
    private C_stateService c_stateService;
    
    /**
     * POST  /c-states : Create a new c_state.
     *
     * @param c_state the c_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_state, or with status 400 (Bad Request) if the c_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state> createC_state(@RequestBody C_state c_state) throws URISyntaxException {
        log.debug("REST request to save C_state : {}", c_state);
        if (c_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_state", "idexists", "A new c_state cannot already have an ID")).body(null);
        }
        C_state result = c_stateService.save(c_state);
        return ResponseEntity.created(new URI("/api/c-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-states : Updates an existing c_state.
     *
     * @param c_state the c_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_state,
     * or with status 400 (Bad Request) if the c_state is not valid,
     * or with status 500 (Internal Server Error) if the c_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state> updateC_state(@RequestBody C_state c_state) throws URISyntaxException {
        log.debug("REST request to update C_state : {}", c_state);
        if (c_state.getId() == null) {
            return createC_state(c_state);
        }
        C_state result = c_stateService.save(c_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_state", c_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-states : get all the c_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_state>> getAllC_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_states");
        Page<C_state> page = c_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-states/:id : get the "id" c_state.
     *
     * @param id the id of the c_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_state> getC_state(@PathVariable Long id) {
        log.debug("REST request to get C_state : {}", id);
        C_state c_state = c_stateService.findOne(id);
        return Optional.ofNullable(c_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-states/:id : delete the "id" c_state.
     *
     * @param id the id of the c_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_state(@PathVariable Long id) {
        log.debug("REST request to delete C_state : {}", id);
        c_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_state", id.toString())).build();
    }

}
