package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Key_entity;
import org.megapractical.billingplatform.service.Key_entityService;
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
 * REST controller for managing Key_entity.
 */
@RestController
@RequestMapping("/api")
public class Key_entityResource {

    private final Logger log = LoggerFactory.getLogger(Key_entityResource.class);
        
    @Inject
    private Key_entityService key_entityService;
    
    /**
     * POST  /key-entities : Create a new key_entity.
     *
     * @param key_entity the key_entity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new key_entity, or with status 400 (Bad Request) if the key_entity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/key-entities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Key_entity> createKey_entity(@Valid @RequestBody Key_entity key_entity) throws URISyntaxException {
        log.debug("REST request to save Key_entity : {}", key_entity);
        if (key_entity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("key_entity", "idexists", "A new key_entity cannot already have an ID")).body(null);
        }
        Key_entity result = key_entityService.save(key_entity);
        return ResponseEntity.created(new URI("/api/key-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("key_entity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /key-entities : Updates an existing key_entity.
     *
     * @param key_entity the key_entity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated key_entity,
     * or with status 400 (Bad Request) if the key_entity is not valid,
     * or with status 500 (Internal Server Error) if the key_entity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/key-entities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Key_entity> updateKey_entity(@Valid @RequestBody Key_entity key_entity) throws URISyntaxException {
        log.debug("REST request to update Key_entity : {}", key_entity);
        if (key_entity.getId() == null) {
            return createKey_entity(key_entity);
        }
        Key_entity result = key_entityService.save(key_entity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("key_entity", key_entity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /key-entities : get all the key_entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of key_entities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/key-entities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Key_entity>> getAllKey_entities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Key_entities");
        Page<Key_entity> page = key_entityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/key-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /key-entities/:id : get the "id" key_entity.
     *
     * @param id the id of the key_entity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the key_entity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/key-entities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Key_entity> getKey_entity(@PathVariable Long id) {
        log.debug("REST request to get Key_entity : {}", id);
        Key_entity key_entity = key_entityService.findOne(id);
        return Optional.ofNullable(key_entity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /key-entities/:id : delete the "id" key_entity.
     *
     * @param id the id of the key_entity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/key-entities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteKey_entity(@PathVariable Long id) {
        log.debug("REST request to delete Key_entity : {}", id);
        key_entityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("key_entity", id.toString())).build();
    }

}
