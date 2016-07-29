package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_type_operation;
import org.megapractical.billingplatform.service.C_type_operationService;
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
 * REST controller for managing C_type_operation.
 */
@RestController
@RequestMapping("/api")
public class C_type_operationResource {

    private final Logger log = LoggerFactory.getLogger(C_type_operationResource.class);
        
    @Inject
    private C_type_operationService c_type_operationService;
    
    /**
     * POST  /c-type-operations : Create a new c_type_operation.
     *
     * @param c_type_operation the c_type_operation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_type_operation, or with status 400 (Bad Request) if the c_type_operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-operations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation> createC_type_operation(@Valid @RequestBody C_type_operation c_type_operation) throws URISyntaxException {
        log.debug("REST request to save C_type_operation : {}", c_type_operation);
        if (c_type_operation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_type_operation", "idexists", "A new c_type_operation cannot already have an ID")).body(null);
        }
        C_type_operation result = c_type_operationService.save(c_type_operation);
        return ResponseEntity.created(new URI("/api/c-type-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_type_operation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-type-operations : Updates an existing c_type_operation.
     *
     * @param c_type_operation the c_type_operation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_type_operation,
     * or with status 400 (Bad Request) if the c_type_operation is not valid,
     * or with status 500 (Internal Server Error) if the c_type_operation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-operations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation> updateC_type_operation(@Valid @RequestBody C_type_operation c_type_operation) throws URISyntaxException {
        log.debug("REST request to update C_type_operation : {}", c_type_operation);
        if (c_type_operation.getId() == null) {
            return createC_type_operation(c_type_operation);
        }
        C_type_operation result = c_type_operationService.save(c_type_operation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_type_operation", c_type_operation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-type-operations : get all the c_type_operations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_type_operations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-type-operations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_type_operation>> getAllC_type_operations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_type_operations");
        Page<C_type_operation> page = c_type_operationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-type-operations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-type-operations/:id : get the "id" c_type_operation.
     *
     * @param id the id of the c_type_operation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_type_operation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-type-operations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation> getC_type_operation(@PathVariable Long id) {
        log.debug("REST request to get C_type_operation : {}", id);
        C_type_operation c_type_operation = c_type_operationService.findOne(id);
        return Optional.ofNullable(c_type_operation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-type-operations/:id : delete the "id" c_type_operation.
     *
     * @param id the id of the c_type_operation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-type-operations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_type_operation(@PathVariable Long id) {
        log.debug("REST request to delete C_type_operation : {}", id);
        c_type_operationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_type_operation", id.toString())).build();
    }

}
