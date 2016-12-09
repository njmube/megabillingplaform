package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_desc_state;
import org.megapractical.billingplatform.service.Com_desc_stateService;
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
 * REST controller for managing Com_desc_state.
 */
@RestController
@RequestMapping("/api")
public class Com_desc_stateResource {

    private final Logger log = LoggerFactory.getLogger(Com_desc_stateResource.class);
        
    @Inject
    private Com_desc_stateService com_desc_stateService;
    
    /**
     * POST  /com-desc-states : Create a new com_desc_state.
     *
     * @param com_desc_state the com_desc_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_desc_state, or with status 400 (Bad Request) if the com_desc_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-desc-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_state> createCom_desc_state(@Valid @RequestBody Com_desc_state com_desc_state) throws URISyntaxException {
        log.debug("REST request to save Com_desc_state : {}", com_desc_state);
        if (com_desc_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_desc_state", "idexists", "A new com_desc_state cannot already have an ID")).body(null);
        }
        Com_desc_state result = com_desc_stateService.save(com_desc_state);
        return ResponseEntity.created(new URI("/api/com-desc-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_desc_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-desc-states : Updates an existing com_desc_state.
     *
     * @param com_desc_state the com_desc_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_desc_state,
     * or with status 400 (Bad Request) if the com_desc_state is not valid,
     * or with status 500 (Internal Server Error) if the com_desc_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-desc-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_state> updateCom_desc_state(@Valid @RequestBody Com_desc_state com_desc_state) throws URISyntaxException {
        log.debug("REST request to update Com_desc_state : {}", com_desc_state);
        if (com_desc_state.getId() == null) {
            return createCom_desc_state(com_desc_state);
        }
        Com_desc_state result = com_desc_stateService.save(com_desc_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_desc_state", com_desc_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-desc-states : get all the com_desc_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_desc_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-desc-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_desc_state>> getAllCom_desc_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_desc_states");
        Page<Com_desc_state> page = com_desc_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-desc-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-desc-states/:id : get the "id" com_desc_state.
     *
     * @param id the id of the com_desc_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_desc_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-desc-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_desc_state> getCom_desc_state(@PathVariable Long id) {
        log.debug("REST request to get Com_desc_state : {}", id);
        Com_desc_state com_desc_state = com_desc_stateService.findOne(id);
        return Optional.ofNullable(com_desc_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-desc-states/:id : delete the "id" com_desc_state.
     *
     * @param id the id of the com_desc_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-desc-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_desc_state(@PathVariable Long id) {
        log.debug("REST request to delete Com_desc_state : {}", id);
        com_desc_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_desc_state", id.toString())).build();
    }

}
