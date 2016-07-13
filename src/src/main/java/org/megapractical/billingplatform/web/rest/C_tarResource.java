package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_tar;
import org.megapractical.billingplatform.service.C_tarService;
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
 * REST controller for managing C_tar.
 */
@RestController
@RequestMapping("/api")
public class C_tarResource {

    private final Logger log = LoggerFactory.getLogger(C_tarResource.class);
        
    @Inject
    private C_tarService c_tarService;
    
    /**
     * POST  /c-tars : Create a new c_tar.
     *
     * @param c_tar the c_tar to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_tar, or with status 400 (Bad Request) if the c_tar has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-tars",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tar> createC_tar(@Valid @RequestBody C_tar c_tar) throws URISyntaxException {
        log.debug("REST request to save C_tar : {}", c_tar);
        if (c_tar.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_tar", "idexists", "A new c_tar cannot already have an ID")).body(null);
        }
        C_tar result = c_tarService.save(c_tar);
        return ResponseEntity.created(new URI("/api/c-tars/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_tar", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-tars : Updates an existing c_tar.
     *
     * @param c_tar the c_tar to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_tar,
     * or with status 400 (Bad Request) if the c_tar is not valid,
     * or with status 500 (Internal Server Error) if the c_tar couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-tars",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tar> updateC_tar(@Valid @RequestBody C_tar c_tar) throws URISyntaxException {
        log.debug("REST request to update C_tar : {}", c_tar);
        if (c_tar.getId() == null) {
            return createC_tar(c_tar);
        }
        C_tar result = c_tarService.save(c_tar);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_tar", c_tar.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-tars : get all the c_tars.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_tars in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-tars",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_tar>> getAllC_tars(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_tars");
        Page<C_tar> page = c_tarService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-tars");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-tars/:id : get the "id" c_tar.
     *
     * @param id the id of the c_tar to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_tar, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-tars/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_tar> getC_tar(@PathVariable Long id) {
        log.debug("REST request to get C_tar : {}", id);
        C_tar c_tar = c_tarService.findOne(id);
        return Optional.ofNullable(c_tar)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-tars/:id : delete the "id" c_tar.
     *
     * @param id the id of the c_tar to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-tars/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_tar(@PathVariable Long id) {
        log.debug("REST request to delete C_tar : {}", id);
        c_tarService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_tar", id.toString())).build();
    }

}
