package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_committee_type;
import org.megapractical.billingplatform.service.C_committee_typeService;
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
 * REST controller for managing C_committee_type.
 */
@RestController
@RequestMapping("/api")
public class C_committee_typeResource {

    private final Logger log = LoggerFactory.getLogger(C_committee_typeResource.class);
        
    @Inject
    private C_committee_typeService c_committee_typeService;
    
    /**
     * POST  /c-committee-types : Create a new c_committee_type.
     *
     * @param c_committee_type the c_committee_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_committee_type, or with status 400 (Bad Request) if the c_committee_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-committee-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_committee_type> createC_committee_type(@Valid @RequestBody C_committee_type c_committee_type) throws URISyntaxException {
        log.debug("REST request to save C_committee_type : {}", c_committee_type);
        if (c_committee_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_committee_type", "idexists", "A new c_committee_type cannot already have an ID")).body(null);
        }
        C_committee_type result = c_committee_typeService.save(c_committee_type);
        return ResponseEntity.created(new URI("/api/c-committee-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_committee_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-committee-types : Updates an existing c_committee_type.
     *
     * @param c_committee_type the c_committee_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_committee_type,
     * or with status 400 (Bad Request) if the c_committee_type is not valid,
     * or with status 500 (Internal Server Error) if the c_committee_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-committee-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_committee_type> updateC_committee_type(@Valid @RequestBody C_committee_type c_committee_type) throws URISyntaxException {
        log.debug("REST request to update C_committee_type : {}", c_committee_type);
        if (c_committee_type.getId() == null) {
            return createC_committee_type(c_committee_type);
        }
        C_committee_type result = c_committee_typeService.save(c_committee_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_committee_type", c_committee_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-committee-types : get all the c_committee_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_committee_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-committee-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_committee_type>> getAllC_committee_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_committee_types");
        Page<C_committee_type> page = c_committee_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-committee-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-committee-types/:id : get the "id" c_committee_type.
     *
     * @param id the id of the c_committee_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_committee_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-committee-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_committee_type> getC_committee_type(@PathVariable Long id) {
        log.debug("REST request to get C_committee_type : {}", id);
        C_committee_type c_committee_type = c_committee_typeService.findOne(id);
        return Optional.ofNullable(c_committee_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-committee-types/:id : delete the "id" c_committee_type.
     *
     * @param id the id of the c_committee_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-committee-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_committee_type(@PathVariable Long id) {
        log.debug("REST request to delete C_committee_type : {}", id);
        c_committee_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_committee_type", id.toString())).build();
    }

}
