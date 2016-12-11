package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_type_estate;
import org.megapractical.billingplatform.service.C_type_estateService;
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
 * REST controller for managing C_type_estate.
 */
@RestController
@RequestMapping("/api")
public class C_type_estateResource {

    private final Logger log = LoggerFactory.getLogger(C_type_estateResource.class);
        
    @Inject
    private C_type_estateService c_type_estateService;
    
    /**
     * POST  /c-type-estates : Create a new c_type_estate.
     *
     * @param c_type_estate the c_type_estate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_type_estate, or with status 400 (Bad Request) if the c_type_estate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-estates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_estate> createC_type_estate(@Valid @RequestBody C_type_estate c_type_estate) throws URISyntaxException {
        log.debug("REST request to save C_type_estate : {}", c_type_estate);
        if (c_type_estate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_type_estate", "idexists", "A new c_type_estate cannot already have an ID")).body(null);
        }
        C_type_estate result = c_type_estateService.save(c_type_estate);
        return ResponseEntity.created(new URI("/api/c-type-estates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_type_estate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-type-estates : Updates an existing c_type_estate.
     *
     * @param c_type_estate the c_type_estate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_type_estate,
     * or with status 400 (Bad Request) if the c_type_estate is not valid,
     * or with status 500 (Internal Server Error) if the c_type_estate couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-type-estates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_estate> updateC_type_estate(@Valid @RequestBody C_type_estate c_type_estate) throws URISyntaxException {
        log.debug("REST request to update C_type_estate : {}", c_type_estate);
        if (c_type_estate.getId() == null) {
            return createC_type_estate(c_type_estate);
        }
        C_type_estate result = c_type_estateService.save(c_type_estate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_type_estate", c_type_estate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-type-estates : get all the c_type_estates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_type_estates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-type-estates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_type_estate>> getAllC_type_estates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_type_estates");
        Page<C_type_estate> page = c_type_estateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-type-estates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-type-estates/:id : get the "id" c_type_estate.
     *
     * @param id the id of the c_type_estate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_type_estate, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-type-estates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_type_estate> getC_type_estate(@PathVariable Long id) {
        log.debug("REST request to get C_type_estate : {}", id);
        C_type_estate c_type_estate = c_type_estateService.findOne(id);
        return Optional.ofNullable(c_type_estate)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-type-estates/:id : delete the "id" c_type_estate.
     *
     * @param id the id of the c_type_estate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-type-estates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_type_estate(@PathVariable Long id) {
        log.debug("REST request to delete C_type_estate : {}", id);
        c_type_estateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_type_estate", id.toString())).build();
    }

}
