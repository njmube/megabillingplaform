package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_data_operation;
import org.megapractical.billingplatform.service.Freecom_data_operationService;
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
 * REST controller for managing Freecom_data_operation.
 */
@RestController
@RequestMapping("/api")
public class Freecom_data_operationResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_data_operationResource.class);
        
    @Inject
    private Freecom_data_operationService freecom_data_operationService;
    
    /**
     * POST  /freecom-data-operations : Create a new freecom_data_operation.
     *
     * @param freecom_data_operation the freecom_data_operation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_data_operation, or with status 400 (Bad Request) if the freecom_data_operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-data-operations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_operation> createFreecom_data_operation(@Valid @RequestBody Freecom_data_operation freecom_data_operation) throws URISyntaxException {
        log.debug("REST request to save Freecom_data_operation : {}", freecom_data_operation);
        if (freecom_data_operation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_data_operation", "idexists", "A new freecom_data_operation cannot already have an ID")).body(null);
        }
        Freecom_data_operation result = freecom_data_operationService.save(freecom_data_operation);
        return ResponseEntity.created(new URI("/api/freecom-data-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_data_operation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-data-operations : Updates an existing freecom_data_operation.
     *
     * @param freecom_data_operation the freecom_data_operation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_data_operation,
     * or with status 400 (Bad Request) if the freecom_data_operation is not valid,
     * or with status 500 (Internal Server Error) if the freecom_data_operation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-data-operations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_operation> updateFreecom_data_operation(@Valid @RequestBody Freecom_data_operation freecom_data_operation) throws URISyntaxException {
        log.debug("REST request to update Freecom_data_operation : {}", freecom_data_operation);
        if (freecom_data_operation.getId() == null) {
            return createFreecom_data_operation(freecom_data_operation);
        }
        Freecom_data_operation result = freecom_data_operationService.save(freecom_data_operation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_data_operation", freecom_data_operation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-data-operations : get all the freecom_data_operations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_data_operations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-data-operations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_data_operation>> getAllFreecom_data_operations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_data_operations");
        Page<Freecom_data_operation> page = freecom_data_operationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-data-operations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-data-operations/:id : get the "id" freecom_data_operation.
     *
     * @param id the id of the freecom_data_operation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_data_operation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-data-operations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_data_operation> getFreecom_data_operation(@PathVariable Long id) {
        log.debug("REST request to get Freecom_data_operation : {}", id);
        Freecom_data_operation freecom_data_operation = freecom_data_operationService.findOne(id);
        return Optional.ofNullable(freecom_data_operation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-data-operations/:id : delete the "id" freecom_data_operation.
     *
     * @param id the id of the freecom_data_operation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-data-operations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_data_operation(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_data_operation : {}", id);
        freecom_data_operationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_data_operation", id.toString())).build();
    }

}
