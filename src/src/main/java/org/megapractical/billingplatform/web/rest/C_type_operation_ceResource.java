package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_type_operation_ce;
import org.megapractical.billingplatform.service.C_type_operation_ceService;
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
 * REST controller for managing C_type_operation_ce.
 */
@RestController
@RequestMapping("/api")
public class C_type_operation_ceResource {

    private final Logger log = LoggerFactory.getLogger(C_type_operation_ceResource.class);
        
    @Inject
    private C_type_operation_ceService c_type_operation_ceService;
    
    /**
     * POST  /c-type-operation-ces : Create a new c_type_operation_ce.
     *
     * @param c_type_operation_ce the c_type_operation_ce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_type_operation_ce, or with status 400 (Bad Request) if the c_type_operation_ce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-operation-ces",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation_ce> createC_type_operation_ce(@Valid @RequestBody C_type_operation_ce c_type_operation_ce) throws URISyntaxException {
        log.debug("REST request to save C_type_operation_ce : {}", c_type_operation_ce);
        if (c_type_operation_ce.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_type_operation_ce", "idexists", "A new c_type_operation_ce cannot already have an ID")).body(null);
        }
        C_type_operation_ce result = c_type_operation_ceService.save(c_type_operation_ce);
        return ResponseEntity.created(new URI("/api/c-type-operation-ces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_type_operation_ce", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-type-operation-ces : Updates an existing c_type_operation_ce.
     *
     * @param c_type_operation_ce the c_type_operation_ce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_type_operation_ce,
     * or with status 400 (Bad Request) if the c_type_operation_ce is not valid,
     * or with status 500 (Internal Server Error) if the c_type_operation_ce couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-operation-ces",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation_ce> updateC_type_operation_ce(@Valid @RequestBody C_type_operation_ce c_type_operation_ce) throws URISyntaxException {
        log.debug("REST request to update C_type_operation_ce : {}", c_type_operation_ce);
        if (c_type_operation_ce.getId() == null) {
            return createC_type_operation_ce(c_type_operation_ce);
        }
        C_type_operation_ce result = c_type_operation_ceService.save(c_type_operation_ce);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_type_operation_ce", c_type_operation_ce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-type-operation-ces : get all the c_type_operation_ces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_type_operation_ces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-type-operation-ces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_type_operation_ce>> getAllC_type_operation_ces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_type_operation_ces");
        Page<C_type_operation_ce> page = c_type_operation_ceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-type-operation-ces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-type-operation-ces/:id : get the "id" c_type_operation_ce.
     *
     * @param id the id of the c_type_operation_ce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_type_operation_ce, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-type-operation-ces/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_operation_ce> getC_type_operation_ce(@PathVariable Long id) {
        log.debug("REST request to get C_type_operation_ce : {}", id);
        C_type_operation_ce c_type_operation_ce = c_type_operation_ceService.findOne(id);
        return Optional.ofNullable(c_type_operation_ce)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-type-operation-ces/:id : delete the "id" c_type_operation_ce.
     *
     * @param id the id of the c_type_operation_ce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-type-operation-ces/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_type_operation_ce(@PathVariable Long id) {
        log.debug("REST request to delete C_type_operation_ce : {}", id);
        c_type_operation_ceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_type_operation_ce", id.toString())).build();
    }

}
