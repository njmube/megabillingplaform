package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_zip_code;
import org.megapractical.billingplatform.service.C_zip_codeService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing C_zip_code.
 */
@RestController
@RequestMapping("/api")
public class C_zip_codeResource {

    private final Logger log = LoggerFactory.getLogger(C_zip_codeResource.class);
        
    @Inject
    private C_zip_codeService c_zip_codeService;
    
    /**
     * POST  /c-zip-codes : Create a new c_zip_code.
     *
     * @param c_zip_code the c_zip_code to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_zip_code, or with status 400 (Bad Request) if the c_zip_code has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-zip-codes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_zip_code> createC_zip_code(@RequestBody C_zip_code c_zip_code) throws URISyntaxException {
        log.debug("REST request to save C_zip_code : {}", c_zip_code);
        if (c_zip_code.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_zip_code", "idexists", "A new c_zip_code cannot already have an ID")).body(null);
        }
        C_zip_code result = c_zip_codeService.save(c_zip_code);
        return ResponseEntity.created(new URI("/api/c-zip-codes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_zip_code", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-zip-codes : Updates an existing c_zip_code.
     *
     * @param c_zip_code the c_zip_code to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_zip_code,
     * or with status 400 (Bad Request) if the c_zip_code is not valid,
     * or with status 500 (Internal Server Error) if the c_zip_code couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-zip-codes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_zip_code> updateC_zip_code(@RequestBody C_zip_code c_zip_code) throws URISyntaxException {
        log.debug("REST request to update C_zip_code : {}", c_zip_code);
        if (c_zip_code.getId() == null) {
            return createC_zip_code(c_zip_code);
        }
        C_zip_code result = c_zip_codeService.save(c_zip_code);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_zip_code", c_zip_code.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-zip-codes : get all the c_zip_codes.
     *
     * @param pageable the pagination information
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of c_zip_codes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-zip-codes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_zip_code>> getAllC_zip_codes(Pageable pageable, @RequestParam(required = false) String filter)
        throws URISyntaxException {
        if ("c_location-is-null".equals(filter)) {
            log.debug("REST request to get all C_zip_codes where c_location is null");
            return new ResponseEntity<>(c_zip_codeService.findAllWhereC_locationIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of C_zip_codes");
        Page<C_zip_code> page = c_zip_codeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-zip-codes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-zip-codes/:id : get the "id" c_zip_code.
     *
     * @param id the id of the c_zip_code to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_zip_code, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-zip-codes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_zip_code> getC_zip_code(@PathVariable Long id) {
        log.debug("REST request to get C_zip_code : {}", id);
        C_zip_code c_zip_code = c_zip_codeService.findOne(id);
        return Optional.ofNullable(c_zip_code)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-zip-codes/:id : delete the "id" c_zip_code.
     *
     * @param id the id of the c_zip_code to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-zip-codes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_zip_code(@PathVariable Long id) {
        log.debug("REST request to delete C_zip_code : {}", id);
        c_zip_codeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_zip_code", id.toString())).build();
    }

}
