package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_transit_type;
import org.megapractical.billingplatform.service.C_transit_typeService;
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
 * REST controller for managing C_transit_type.
 */
@RestController
@RequestMapping("/api")
public class C_transit_typeResource {

    private final Logger log = LoggerFactory.getLogger(C_transit_typeResource.class);
        
    @Inject
    private C_transit_typeService c_transit_typeService;
    
    /**
     * POST  /c-transit-types : Create a new c_transit_type.
     *
     * @param c_transit_type the c_transit_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_transit_type, or with status 400 (Bad Request) if the c_transit_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-transit-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_transit_type> createC_transit_type(@Valid @RequestBody C_transit_type c_transit_type) throws URISyntaxException {
        log.debug("REST request to save C_transit_type : {}", c_transit_type);
        if (c_transit_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_transit_type", "idexists", "A new c_transit_type cannot already have an ID")).body(null);
        }
        C_transit_type result = c_transit_typeService.save(c_transit_type);
        return ResponseEntity.created(new URI("/api/c-transit-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_transit_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-transit-types : Updates an existing c_transit_type.
     *
     * @param c_transit_type the c_transit_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_transit_type,
     * or with status 400 (Bad Request) if the c_transit_type is not valid,
     * or with status 500 (Internal Server Error) if the c_transit_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-transit-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_transit_type> updateC_transit_type(@Valid @RequestBody C_transit_type c_transit_type) throws URISyntaxException {
        log.debug("REST request to update C_transit_type : {}", c_transit_type);
        if (c_transit_type.getId() == null) {
            return createC_transit_type(c_transit_type);
        }
        C_transit_type result = c_transit_typeService.save(c_transit_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_transit_type", c_transit_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-transit-types : get all the c_transit_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_transit_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-transit-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_transit_type>> getAllC_transit_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_transit_types");
        Page<C_transit_type> page = c_transit_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-transit-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-transit-types/:id : get the "id" c_transit_type.
     *
     * @param id the id of the c_transit_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_transit_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-transit-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_transit_type> getC_transit_type(@PathVariable Long id) {
        log.debug("REST request to get C_transit_type : {}", id);
        C_transit_type c_transit_type = c_transit_typeService.findOne(id);
        return Optional.ofNullable(c_transit_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-transit-types/:id : delete the "id" c_transit_type.
     *
     * @param id the id of the c_transit_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-transit-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_transit_type(@PathVariable Long id) {
        log.debug("REST request to delete C_transit_type : {}", id);
        c_transit_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_transit_type", id.toString())).build();
    }

}
