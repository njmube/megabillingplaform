package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Scope;
import org.megapractical.billingplatform.service.ScopeService;
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
 * REST controller for managing Scope.
 */
@RestController
@RequestMapping("/api")
public class ScopeResource {

    private final Logger log = LoggerFactory.getLogger(ScopeResource.class);
        
    @Inject
    private ScopeService scopeService;
    
    /**
     * POST  /scopes : Create a new scope.
     *
     * @param scope the scope to create
     * @return the ResponseEntity with status 201 (Created) and with body the new scope, or with status 400 (Bad Request) if the scope has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scopes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scope> createScope(@Valid @RequestBody Scope scope) throws URISyntaxException {
        log.debug("REST request to save Scope : {}", scope);
        if (scope.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("scope", "idexists", "A new scope cannot already have an ID")).body(null);
        }
        Scope result = scopeService.save(scope);
        return ResponseEntity.created(new URI("/api/scopes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("scope", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /scopes : Updates an existing scope.
     *
     * @param scope the scope to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated scope,
     * or with status 400 (Bad Request) if the scope is not valid,
     * or with status 500 (Internal Server Error) if the scope couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/scopes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scope> updateScope(@Valid @RequestBody Scope scope) throws URISyntaxException {
        log.debug("REST request to update Scope : {}", scope);
        if (scope.getId() == null) {
            return createScope(scope);
        }
        Scope result = scopeService.save(scope);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("scope", scope.getId().toString()))
            .body(result);
    }

    /**
     * GET  /scopes : get all the scopes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of scopes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/scopes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Scope>> getAllScopes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Scopes");
        Page<Scope> page = scopeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/scopes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /scopes/:id : get the "id" scope.
     *
     * @param id the id of the scope to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the scope, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/scopes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Scope> getScope(@PathVariable Long id) {
        log.debug("REST request to get Scope : {}", id);
        Scope scope = scopeService.findOne(id);
        return Optional.ofNullable(scope)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /scopes/:id : delete the "id" scope.
     *
     * @param id the id of the scope to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/scopes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteScope(@PathVariable Long id) {
        log.debug("REST request to delete Scope : {}", id);
        scopeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("scope", id.toString())).build();
    }

}
