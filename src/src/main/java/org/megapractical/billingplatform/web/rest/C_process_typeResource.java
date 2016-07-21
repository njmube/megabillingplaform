package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_process_type;
import org.megapractical.billingplatform.service.C_process_typeService;
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
 * REST controller for managing C_process_type.
 */
@RestController
@RequestMapping("/api")
public class C_process_typeResource {

    private final Logger log = LoggerFactory.getLogger(C_process_typeResource.class);
        
    @Inject
    private C_process_typeService c_process_typeService;
    
    /**
     * POST  /c-process-types : Create a new c_process_type.
     *
     * @param c_process_type the c_process_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_process_type, or with status 400 (Bad Request) if the c_process_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-process-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_process_type> createC_process_type(@Valid @RequestBody C_process_type c_process_type) throws URISyntaxException {
        log.debug("REST request to save C_process_type : {}", c_process_type);
        if (c_process_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_process_type", "idexists", "A new c_process_type cannot already have an ID")).body(null);
        }
        C_process_type result = c_process_typeService.save(c_process_type);
        return ResponseEntity.created(new URI("/api/c-process-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_process_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-process-types : Updates an existing c_process_type.
     *
     * @param c_process_type the c_process_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_process_type,
     * or with status 400 (Bad Request) if the c_process_type is not valid,
     * or with status 500 (Internal Server Error) if the c_process_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-process-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_process_type> updateC_process_type(@Valid @RequestBody C_process_type c_process_type) throws URISyntaxException {
        log.debug("REST request to update C_process_type : {}", c_process_type);
        if (c_process_type.getId() == null) {
            return createC_process_type(c_process_type);
        }
        C_process_type result = c_process_typeService.save(c_process_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_process_type", c_process_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-process-types : get all the c_process_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_process_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-process-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_process_type>> getAllC_process_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_process_types");
        Page<C_process_type> page = c_process_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-process-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-process-types/:id : get the "id" c_process_type.
     *
     * @param id the id of the c_process_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_process_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-process-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_process_type> getC_process_type(@PathVariable Long id) {
        log.debug("REST request to get C_process_type : {}", id);
        C_process_type c_process_type = c_process_typeService.findOne(id);
        return Optional.ofNullable(c_process_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-process-types/:id : delete the "id" c_process_type.
     *
     * @param id the id of the c_process_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-process-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_process_type(@PathVariable Long id) {
        log.debug("REST request to delete C_process_type : {}", id);
        c_process_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_process_type", id.toString())).build();
    }

}
