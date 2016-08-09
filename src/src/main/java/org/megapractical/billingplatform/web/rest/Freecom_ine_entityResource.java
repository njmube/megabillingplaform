package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_ine_entity;
import org.megapractical.billingplatform.service.Freecom_ine_entityService;
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

/**
 * REST controller for managing Freecom_ine_entity.
 */
@RestController
@RequestMapping("/api")
public class Freecom_ine_entityResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_ine_entityResource.class);
        
    @Inject
    private Freecom_ine_entityService freecom_ine_entityService;
    
    /**
     * POST  /freecom-ine-entities : Create a new freecom_ine_entity.
     *
     * @param freecom_ine_entity the freecom_ine_entity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_ine_entity, or with status 400 (Bad Request) if the freecom_ine_entity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ine-entities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine_entity> createFreecom_ine_entity(@RequestBody Freecom_ine_entity freecom_ine_entity) throws URISyntaxException {
        log.debug("REST request to save Freecom_ine_entity : {}", freecom_ine_entity);
        if (freecom_ine_entity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_ine_entity", "idexists", "A new freecom_ine_entity cannot already have an ID")).body(null);
        }
        Freecom_ine_entity result = freecom_ine_entityService.save(freecom_ine_entity);
        return ResponseEntity.created(new URI("/api/freecom-ine-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_ine_entity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-ine-entities : Updates an existing freecom_ine_entity.
     *
     * @param freecom_ine_entity the freecom_ine_entity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_ine_entity,
     * or with status 400 (Bad Request) if the freecom_ine_entity is not valid,
     * or with status 500 (Internal Server Error) if the freecom_ine_entity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ine-entities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine_entity> updateFreecom_ine_entity(@RequestBody Freecom_ine_entity freecom_ine_entity) throws URISyntaxException {
        log.debug("REST request to update Freecom_ine_entity : {}", freecom_ine_entity);
        if (freecom_ine_entity.getId() == null) {
            return createFreecom_ine_entity(freecom_ine_entity);
        }
        Freecom_ine_entity result = freecom_ine_entityService.save(freecom_ine_entity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_ine_entity", freecom_ine_entity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-ine-entities : get all the freecom_ine_entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_ine_entities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-ine-entities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_ine_entity>> getAllFreecom_ine_entities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_ine_entities");
        Page<Freecom_ine_entity> page = freecom_ine_entityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-ine-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-ine-entities/:id : get the "id" freecom_ine_entity.
     *
     * @param id the id of the freecom_ine_entity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_ine_entity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-ine-entities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ine_entity> getFreecom_ine_entity(@PathVariable Long id) {
        log.debug("REST request to get Freecom_ine_entity : {}", id);
        Freecom_ine_entity freecom_ine_entity = freecom_ine_entityService.findOne(id);
        return Optional.ofNullable(freecom_ine_entity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-ine-entities/:id : delete the "id" freecom_ine_entity.
     *
     * @param id the id of the freecom_ine_entity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-ine-entities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_ine_entity(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_ine_entity : {}", id);
        freecom_ine_entityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_ine_entity", id.toString())).build();
    }

}
