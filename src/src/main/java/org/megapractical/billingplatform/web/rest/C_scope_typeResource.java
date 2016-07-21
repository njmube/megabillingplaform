package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_scope_type;
import org.megapractical.billingplatform.service.C_scope_typeService;
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
 * REST controller for managing C_scope_type.
 */
@RestController
@RequestMapping("/api")
public class C_scope_typeResource {

    private final Logger log = LoggerFactory.getLogger(C_scope_typeResource.class);
        
    @Inject
    private C_scope_typeService c_scope_typeService;
    
    /**
     * POST  /c-scope-types : Create a new c_scope_type.
     *
     * @param c_scope_type the c_scope_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_scope_type, or with status 400 (Bad Request) if the c_scope_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-scope-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_scope_type> createC_scope_type(@Valid @RequestBody C_scope_type c_scope_type) throws URISyntaxException {
        log.debug("REST request to save C_scope_type : {}", c_scope_type);
        if (c_scope_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_scope_type", "idexists", "A new c_scope_type cannot already have an ID")).body(null);
        }
        C_scope_type result = c_scope_typeService.save(c_scope_type);
        return ResponseEntity.created(new URI("/api/c-scope-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_scope_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-scope-types : Updates an existing c_scope_type.
     *
     * @param c_scope_type the c_scope_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_scope_type,
     * or with status 400 (Bad Request) if the c_scope_type is not valid,
     * or with status 500 (Internal Server Error) if the c_scope_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-scope-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_scope_type> updateC_scope_type(@Valid @RequestBody C_scope_type c_scope_type) throws URISyntaxException {
        log.debug("REST request to update C_scope_type : {}", c_scope_type);
        if (c_scope_type.getId() == null) {
            return createC_scope_type(c_scope_type);
        }
        C_scope_type result = c_scope_typeService.save(c_scope_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_scope_type", c_scope_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-scope-types : get all the c_scope_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_scope_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-scope-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_scope_type>> getAllC_scope_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_scope_types");
        Page<C_scope_type> page = c_scope_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-scope-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-scope-types/:id : get the "id" c_scope_type.
     *
     * @param id the id of the c_scope_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_scope_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-scope-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_scope_type> getC_scope_type(@PathVariable Long id) {
        log.debug("REST request to get C_scope_type : {}", id);
        C_scope_type c_scope_type = c_scope_typeService.findOne(id);
        return Optional.ofNullable(c_scope_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-scope-types/:id : delete the "id" c_scope_type.
     *
     * @param id the id of the c_scope_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-scope-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_scope_type(@PathVariable Long id) {
        log.debug("REST request to delete C_scope_type : {}", id);
        c_scope_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_scope_type", id.toString())).build();
    }

}
