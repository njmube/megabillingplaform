package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Type_state;
import org.megapractical.billingplatform.service.Type_stateService;
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
 * REST controller for managing Type_state.
 */
@RestController
@RequestMapping("/api")
public class Type_stateResource {

    private final Logger log = LoggerFactory.getLogger(Type_stateResource.class);
        
    @Inject
    private Type_stateService type_stateService;
    
    /**
     * POST  /type-states : Create a new type_state.
     *
     * @param type_state the type_state to create
     * @return the ResponseEntity with status 201 (Created) and with body the new type_state, or with status 400 (Bad Request) if the type_state has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-states",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_state> createType_state(@Valid @RequestBody Type_state type_state) throws URISyntaxException {
        log.debug("REST request to save Type_state : {}", type_state);
        if (type_state.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("type_state", "idexists", "A new type_state cannot already have an ID")).body(null);
        }
        Type_state result = type_stateService.save(type_state);
        return ResponseEntity.created(new URI("/api/type-states/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("type_state", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-states : Updates an existing type_state.
     *
     * @param type_state the type_state to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated type_state,
     * or with status 400 (Bad Request) if the type_state is not valid,
     * or with status 500 (Internal Server Error) if the type_state couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/type-states",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_state> updateType_state(@Valid @RequestBody Type_state type_state) throws URISyntaxException {
        log.debug("REST request to update Type_state : {}", type_state);
        if (type_state.getId() == null) {
            return createType_state(type_state);
        }
        Type_state result = type_stateService.save(type_state);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("type_state", type_state.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-states : get all the type_states.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of type_states in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/type-states",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Type_state>> getAllType_states(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Type_states");
        Page<Type_state> page = type_stateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-states");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-states/:id : get the "id" type_state.
     *
     * @param id the id of the type_state to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the type_state, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/type-states/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Type_state> getType_state(@PathVariable Long id) {
        log.debug("REST request to get Type_state : {}", id);
        Type_state type_state = type_stateService.findOne(id);
        return Optional.ofNullable(type_state)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /type-states/:id : delete the "id" type_state.
     *
     * @param id the id of the type_state to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/type-states/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteType_state(@PathVariable Long id) {
        log.debug("REST request to delete Type_state : {}", id);
        type_stateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("type_state", id.toString())).build();
    }

}
