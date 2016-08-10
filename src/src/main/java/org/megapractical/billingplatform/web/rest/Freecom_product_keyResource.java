package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_product_key;
import org.megapractical.billingplatform.service.Freecom_product_keyService;
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
 * REST controller for managing Freecom_product_key.
 */
@RestController
@RequestMapping("/api")
public class Freecom_product_keyResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_product_keyResource.class);
        
    @Inject
    private Freecom_product_keyService freecom_product_keyService;
    
    /**
     * POST  /freecom-product-keys : Create a new freecom_product_key.
     *
     * @param freecom_product_key the freecom_product_key to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_product_key, or with status 400 (Bad Request) if the freecom_product_key has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-product-keys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_product_key> createFreecom_product_key(@Valid @RequestBody Freecom_product_key freecom_product_key) throws URISyntaxException {
        log.debug("REST request to save Freecom_product_key : {}", freecom_product_key);
        if (freecom_product_key.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_product_key", "idexists", "A new freecom_product_key cannot already have an ID")).body(null);
        }
        Freecom_product_key result = freecom_product_keyService.save(freecom_product_key);
        return ResponseEntity.created(new URI("/api/freecom-product-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_product_key", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-product-keys : Updates an existing freecom_product_key.
     *
     * @param freecom_product_key the freecom_product_key to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_product_key,
     * or with status 400 (Bad Request) if the freecom_product_key is not valid,
     * or with status 500 (Internal Server Error) if the freecom_product_key couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-product-keys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_product_key> updateFreecom_product_key(@Valid @RequestBody Freecom_product_key freecom_product_key) throws URISyntaxException {
        log.debug("REST request to update Freecom_product_key : {}", freecom_product_key);
        if (freecom_product_key.getId() == null) {
            return createFreecom_product_key(freecom_product_key);
        }
        Freecom_product_key result = freecom_product_keyService.save(freecom_product_key);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_product_key", freecom_product_key.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-product-keys : get all the freecom_product_keys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_product_keys in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-product-keys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_product_key>> getAllFreecom_product_keys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_product_keys");
        Page<Freecom_product_key> page = freecom_product_keyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-product-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-product-keys/:id : get the "id" freecom_product_key.
     *
     * @param id the id of the freecom_product_key to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_product_key, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-product-keys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_product_key> getFreecom_product_key(@PathVariable Long id) {
        log.debug("REST request to get Freecom_product_key : {}", id);
        Freecom_product_key freecom_product_key = freecom_product_keyService.findOne(id);
        return Optional.ofNullable(freecom_product_key)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-product-keys/:id : delete the "id" freecom_product_key.
     *
     * @param id the id of the freecom_product_key to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-product-keys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_product_key(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_product_key : {}", id);
        freecom_product_keyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_product_key", id.toString())).build();
    }

}
