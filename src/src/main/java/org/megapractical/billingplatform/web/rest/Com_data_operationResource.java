package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_data_operation;
import org.megapractical.billingplatform.service.Com_data_operationService;
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
 * REST controller for managing Com_data_operation.
 */
@RestController
@RequestMapping("/api")
public class Com_data_operationResource {

    private final Logger log = LoggerFactory.getLogger(Com_data_operationResource.class);
        
    @Inject
    private Com_data_operationService com_data_operationService;
    
    /**
     * POST  /com-data-operations : Create a new com_data_operation.
     *
     * @param com_data_operation the com_data_operation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_data_operation, or with status 400 (Bad Request) if the com_data_operation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-data-operations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_operation> createCom_data_operation(@Valid @RequestBody Com_data_operation com_data_operation) throws URISyntaxException {
        log.debug("REST request to save Com_data_operation : {}", com_data_operation);
        if (com_data_operation.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_data_operation", "idexists", "A new com_data_operation cannot already have an ID")).body(null);
        }
        Com_data_operation result = com_data_operationService.save(com_data_operation);
        return ResponseEntity.created(new URI("/api/com-data-operations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_data_operation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-data-operations : Updates an existing com_data_operation.
     *
     * @param com_data_operation the com_data_operation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_data_operation,
     * or with status 400 (Bad Request) if the com_data_operation is not valid,
     * or with status 500 (Internal Server Error) if the com_data_operation couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-data-operations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_operation> updateCom_data_operation(@Valid @RequestBody Com_data_operation com_data_operation) throws URISyntaxException {
        log.debug("REST request to update Com_data_operation : {}", com_data_operation);
        if (com_data_operation.getId() == null) {
            return createCom_data_operation(com_data_operation);
        }
        Com_data_operation result = com_data_operationService.save(com_data_operation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_data_operation", com_data_operation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-data-operations : get all the com_data_operations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_data_operations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-data-operations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_data_operation>> getAllCom_data_operations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_data_operations");
        Page<Com_data_operation> page = com_data_operationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-data-operations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-data-operations/:id : get the "id" com_data_operation.
     *
     * @param id the id of the com_data_operation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_data_operation, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-data-operations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_data_operation> getCom_data_operation(@PathVariable Long id) {
        log.debug("REST request to get Com_data_operation : {}", id);
        Com_data_operation com_data_operation = com_data_operationService.findOne(id);
        return Optional.ofNullable(com_data_operation)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-data-operations/:id : delete the "id" com_data_operation.
     *
     * @param id the id of the com_data_operation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-data-operations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_data_operation(@PathVariable Long id) {
        log.debug("REST request to delete Com_data_operation : {}", id);
        com_data_operationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_data_operation", id.toString())).build();
    }

}
