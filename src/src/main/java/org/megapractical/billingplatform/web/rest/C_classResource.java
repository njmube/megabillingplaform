package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_class;
import org.megapractical.billingplatform.service.C_classService;
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
 * REST controller for managing C_class.
 */
@RestController
@RequestMapping("/api")
public class C_classResource {

    private final Logger log = LoggerFactory.getLogger(C_classResource.class);
        
    @Inject
    private C_classService c_classService;
    
    /**
     * POST  /c-classes : Create a new c_class.
     *
     * @param c_class the c_class to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_class, or with status 400 (Bad Request) if the c_class has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-classes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_class> createC_class(@Valid @RequestBody C_class c_class) throws URISyntaxException {
        log.debug("REST request to save C_class : {}", c_class);
        if (c_class.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_class", "idexists", "A new c_class cannot already have an ID")).body(null);
        }
        C_class result = c_classService.save(c_class);
        return ResponseEntity.created(new URI("/api/c-classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_class", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-classes : Updates an existing c_class.
     *
     * @param c_class the c_class to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_class,
     * or with status 400 (Bad Request) if the c_class is not valid,
     * or with status 500 (Internal Server Error) if the c_class couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-classes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_class> updateC_class(@Valid @RequestBody C_class c_class) throws URISyntaxException {
        log.debug("REST request to update C_class : {}", c_class);
        if (c_class.getId() == null) {
            return createC_class(c_class);
        }
        C_class result = c_classService.save(c_class);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_class", c_class.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-classes : get all the c_classes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_classes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-classes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_class>> getAllC_classes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_classes");
        Page<C_class> page = c_classService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-classes/:id : get the "id" c_class.
     *
     * @param id the id of the c_class to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_class, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-classes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_class> getC_class(@PathVariable Long id) {
        log.debug("REST request to get C_class : {}", id);
        C_class c_class = c_classService.findOne(id);
        return Optional.ofNullable(c_class)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-classes/:id : delete the "id" c_class.
     *
     * @param id the id of the c_class to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-classes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_class(@PathVariable Long id) {
        log.debug("REST request to delete C_class : {}", id);
        c_classService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_class", id.toString())).build();
    }

}
