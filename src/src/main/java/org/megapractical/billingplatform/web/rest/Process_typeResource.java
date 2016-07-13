package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Process_type;
import org.megapractical.billingplatform.service.Process_typeService;
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
 * REST controller for managing Process_type.
 */
@RestController
@RequestMapping("/api")
public class Process_typeResource {

    private final Logger log = LoggerFactory.getLogger(Process_typeResource.class);
        
    @Inject
    private Process_typeService process_typeService;
    
    /**
     * POST  /process-types : Create a new process_type.
     *
     * @param process_type the process_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new process_type, or with status 400 (Bad Request) if the process_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/process-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Process_type> createProcess_type(@Valid @RequestBody Process_type process_type) throws URISyntaxException {
        log.debug("REST request to save Process_type : {}", process_type);
        if (process_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("process_type", "idexists", "A new process_type cannot already have an ID")).body(null);
        }
        Process_type result = process_typeService.save(process_type);
        return ResponseEntity.created(new URI("/api/process-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("process_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /process-types : Updates an existing process_type.
     *
     * @param process_type the process_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated process_type,
     * or with status 400 (Bad Request) if the process_type is not valid,
     * or with status 500 (Internal Server Error) if the process_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/process-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Process_type> updateProcess_type(@Valid @RequestBody Process_type process_type) throws URISyntaxException {
        log.debug("REST request to update Process_type : {}", process_type);
        if (process_type.getId() == null) {
            return createProcess_type(process_type);
        }
        Process_type result = process_typeService.save(process_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("process_type", process_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /process-types : get all the process_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of process_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/process-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Process_type>> getAllProcess_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Process_types");
        Page<Process_type> page = process_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/process-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /process-types/:id : get the "id" process_type.
     *
     * @param id the id of the process_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the process_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/process-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Process_type> getProcess_type(@PathVariable Long id) {
        log.debug("REST request to get Process_type : {}", id);
        Process_type process_type = process_typeService.findOne(id);
        return Optional.ofNullable(process_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /process-types/:id : delete the "id" process_type.
     *
     * @param id the id of the process_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/process-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProcess_type(@PathVariable Long id) {
        log.debug("REST request to delete Process_type : {}", id);
        process_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("process_type", id.toString())).build();
    }

}
