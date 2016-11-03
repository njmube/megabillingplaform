package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_product_key;
import org.megapractical.billingplatform.service.Com_product_keyService;
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
 * REST controller for managing Com_product_key.
 */
@RestController
@RequestMapping("/api")
public class Com_product_keyResource {

    private final Logger log = LoggerFactory.getLogger(Com_product_keyResource.class);
        
    @Inject
    private Com_product_keyService com_product_keyService;
    
    /**
     * POST  /com-product-keys : Create a new com_product_key.
     *
     * @param com_product_key the com_product_key to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_product_key, or with status 400 (Bad Request) if the com_product_key has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-product-keys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_product_key> createCom_product_key(@Valid @RequestBody Com_product_key com_product_key) throws URISyntaxException {
        log.debug("REST request to save Com_product_key : {}", com_product_key);
        if (com_product_key.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_product_key", "idexists", "A new com_product_key cannot already have an ID")).body(null);
        }
        Com_product_key result = com_product_keyService.save(com_product_key);
        return ResponseEntity.created(new URI("/api/com-product-keys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_product_key", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-product-keys : Updates an existing com_product_key.
     *
     * @param com_product_key the com_product_key to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_product_key,
     * or with status 400 (Bad Request) if the com_product_key is not valid,
     * or with status 500 (Internal Server Error) if the com_product_key couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-product-keys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_product_key> updateCom_product_key(@Valid @RequestBody Com_product_key com_product_key) throws URISyntaxException {
        log.debug("REST request to update Com_product_key : {}", com_product_key);
        if (com_product_key.getId() == null) {
            return createCom_product_key(com_product_key);
        }
        Com_product_key result = com_product_keyService.save(com_product_key);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_product_key", com_product_key.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-product-keys : get all the com_product_keys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_product_keys in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-product-keys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_product_key>> getAllCom_product_keys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_product_keys");
        Page<Com_product_key> page = com_product_keyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-product-keys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-product-keys/:id : get the "id" com_product_key.
     *
     * @param id the id of the com_product_key to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_product_key, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-product-keys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_product_key> getCom_product_key(@PathVariable Long id) {
        log.debug("REST request to get Com_product_key : {}", id);
        Com_product_key com_product_key = com_product_keyService.findOne(id);
        return Optional.ofNullable(com_product_key)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-product-keys/:id : delete the "id" com_product_key.
     *
     * @param id the id of the com_product_key to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-product-keys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_product_key(@PathVariable Long id) {
        log.debug("REST request to delete Com_product_key : {}", id);
        com_product_keyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_product_key", id.toString())).build();
    }

}
