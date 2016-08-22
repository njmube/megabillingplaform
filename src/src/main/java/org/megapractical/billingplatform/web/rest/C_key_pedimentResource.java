package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_key_pediment;
import org.megapractical.billingplatform.service.C_key_pedimentService;
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
 * REST controller for managing C_key_pediment.
 */
@RestController
@RequestMapping("/api")
public class C_key_pedimentResource {

    private final Logger log = LoggerFactory.getLogger(C_key_pedimentResource.class);
        
    @Inject
    private C_key_pedimentService c_key_pedimentService;
    
    /**
     * POST  /c-key-pediments : Create a new c_key_pediment.
     *
     * @param c_key_pediment the c_key_pediment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_key_pediment, or with status 400 (Bad Request) if the c_key_pediment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-key-pediments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_key_pediment> createC_key_pediment(@Valid @RequestBody C_key_pediment c_key_pediment) throws URISyntaxException {
        log.debug("REST request to save C_key_pediment : {}", c_key_pediment);
        if (c_key_pediment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_key_pediment", "idexists", "A new c_key_pediment cannot already have an ID")).body(null);
        }
        C_key_pediment result = c_key_pedimentService.save(c_key_pediment);
        return ResponseEntity.created(new URI("/api/c-key-pediments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_key_pediment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-key-pediments : Updates an existing c_key_pediment.
     *
     * @param c_key_pediment the c_key_pediment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_key_pediment,
     * or with status 400 (Bad Request) if the c_key_pediment is not valid,
     * or with status 500 (Internal Server Error) if the c_key_pediment couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-key-pediments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_key_pediment> updateC_key_pediment(@Valid @RequestBody C_key_pediment c_key_pediment) throws URISyntaxException {
        log.debug("REST request to update C_key_pediment : {}", c_key_pediment);
        if (c_key_pediment.getId() == null) {
            return createC_key_pediment(c_key_pediment);
        }
        C_key_pediment result = c_key_pedimentService.save(c_key_pediment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_key_pediment", c_key_pediment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-key-pediments : get all the c_key_pediments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_key_pediments in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-key-pediments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_key_pediment>> getAllC_key_pediments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_key_pediments");
        Page<C_key_pediment> page = c_key_pedimentService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-key-pediments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-key-pediments/:id : get the "id" c_key_pediment.
     *
     * @param id the id of the c_key_pediment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_key_pediment, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-key-pediments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_key_pediment> getC_key_pediment(@PathVariable Long id) {
        log.debug("REST request to get C_key_pediment : {}", id);
        C_key_pediment c_key_pediment = c_key_pedimentService.findOne(id);
        return Optional.ofNullable(c_key_pediment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-key-pediments/:id : delete the "id" c_key_pediment.
     *
     * @param id the id of the c_key_pediment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-key-pediments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_key_pediment(@PathVariable Long id) {
        log.debug("REST request to delete C_key_pediment : {}", id);
        c_key_pedimentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_key_pediment", id.toString())).build();
    }

}
