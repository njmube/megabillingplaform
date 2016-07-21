package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_well_type;
import org.megapractical.billingplatform.service.C_well_typeService;
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
 * REST controller for managing C_well_type.
 */
@RestController
@RequestMapping("/api")
public class C_well_typeResource {

    private final Logger log = LoggerFactory.getLogger(C_well_typeResource.class);
        
    @Inject
    private C_well_typeService c_well_typeService;
    
    /**
     * POST  /c-well-types : Create a new c_well_type.
     *
     * @param c_well_type the c_well_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_well_type, or with status 400 (Bad Request) if the c_well_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-well-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_well_type> createC_well_type(@Valid @RequestBody C_well_type c_well_type) throws URISyntaxException {
        log.debug("REST request to save C_well_type : {}", c_well_type);
        if (c_well_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_well_type", "idexists", "A new c_well_type cannot already have an ID")).body(null);
        }
        C_well_type result = c_well_typeService.save(c_well_type);
        return ResponseEntity.created(new URI("/api/c-well-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_well_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-well-types : Updates an existing c_well_type.
     *
     * @param c_well_type the c_well_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_well_type,
     * or with status 400 (Bad Request) if the c_well_type is not valid,
     * or with status 500 (Internal Server Error) if the c_well_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-well-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_well_type> updateC_well_type(@Valid @RequestBody C_well_type c_well_type) throws URISyntaxException {
        log.debug("REST request to update C_well_type : {}", c_well_type);
        if (c_well_type.getId() == null) {
            return createC_well_type(c_well_type);
        }
        C_well_type result = c_well_typeService.save(c_well_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_well_type", c_well_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-well-types : get all the c_well_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_well_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-well-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_well_type>> getAllC_well_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_well_types");
        Page<C_well_type> page = c_well_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-well-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-well-types/:id : get the "id" c_well_type.
     *
     * @param id the id of the c_well_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_well_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-well-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_well_type> getC_well_type(@PathVariable Long id) {
        log.debug("REST request to get C_well_type : {}", id);
        C_well_type c_well_type = c_well_typeService.findOne(id);
        return Optional.ofNullable(c_well_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-well-types/:id : delete the "id" c_well_type.
     *
     * @param id the id of the c_well_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-well-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_well_type(@PathVariable Long id) {
        log.debug("REST request to delete C_well_type : {}", id);
        c_well_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_well_type", id.toString())).build();
    }

}
