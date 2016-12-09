package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Public_notaries_federal_entity;
import org.megapractical.billingplatform.service.Public_notaries_federal_entityService;
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
 * REST controller for managing Public_notaries_federal_entity.
 */
@RestController
@RequestMapping("/api")
public class Public_notaries_federal_entityResource {

    private final Logger log = LoggerFactory.getLogger(Public_notaries_federal_entityResource.class);
        
    @Inject
    private Public_notaries_federal_entityService public_notaries_federal_entityService;
    
    /**
     * POST  /public-notaries-federal-entities : Create a new public_notaries_federal_entity.
     *
     * @param public_notaries_federal_entity the public_notaries_federal_entity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new public_notaries_federal_entity, or with status 400 (Bad Request) if the public_notaries_federal_entity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/public-notaries-federal-entities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Public_notaries_federal_entity> createPublic_notaries_federal_entity(@Valid @RequestBody Public_notaries_federal_entity public_notaries_federal_entity) throws URISyntaxException {
        log.debug("REST request to save Public_notaries_federal_entity : {}", public_notaries_federal_entity);
        if (public_notaries_federal_entity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("public_notaries_federal_entity", "idexists", "A new public_notaries_federal_entity cannot already have an ID")).body(null);
        }
        Public_notaries_federal_entity result = public_notaries_federal_entityService.save(public_notaries_federal_entity);
        return ResponseEntity.created(new URI("/api/public-notaries-federal-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("public_notaries_federal_entity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /public-notaries-federal-entities : Updates an existing public_notaries_federal_entity.
     *
     * @param public_notaries_federal_entity the public_notaries_federal_entity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated public_notaries_federal_entity,
     * or with status 400 (Bad Request) if the public_notaries_federal_entity is not valid,
     * or with status 500 (Internal Server Error) if the public_notaries_federal_entity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/public-notaries-federal-entities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Public_notaries_federal_entity> updatePublic_notaries_federal_entity(@Valid @RequestBody Public_notaries_federal_entity public_notaries_federal_entity) throws URISyntaxException {
        log.debug("REST request to update Public_notaries_federal_entity : {}", public_notaries_federal_entity);
        if (public_notaries_federal_entity.getId() == null) {
            return createPublic_notaries_federal_entity(public_notaries_federal_entity);
        }
        Public_notaries_federal_entity result = public_notaries_federal_entityService.save(public_notaries_federal_entity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("public_notaries_federal_entity", public_notaries_federal_entity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /public-notaries-federal-entities : get all the public_notaries_federal_entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of public_notaries_federal_entities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/public-notaries-federal-entities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Public_notaries_federal_entity>> getAllPublic_notaries_federal_entities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Public_notaries_federal_entities");
        Page<Public_notaries_federal_entity> page = public_notaries_federal_entityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/public-notaries-federal-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /public-notaries-federal-entities/:id : get the "id" public_notaries_federal_entity.
     *
     * @param id the id of the public_notaries_federal_entity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the public_notaries_federal_entity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/public-notaries-federal-entities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Public_notaries_federal_entity> getPublic_notaries_federal_entity(@PathVariable Long id) {
        log.debug("REST request to get Public_notaries_federal_entity : {}", id);
        Public_notaries_federal_entity public_notaries_federal_entity = public_notaries_federal_entityService.findOne(id);
        return Optional.ofNullable(public_notaries_federal_entity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /public-notaries-federal-entities/:id : delete the "id" public_notaries_federal_entity.
     *
     * @param id the id of the public_notaries_federal_entity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/public-notaries-federal-entities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePublic_notaries_federal_entity(@PathVariable Long id) {
        log.debug("REST request to delete Public_notaries_federal_entity : {}", id);
        public_notaries_federal_entityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("public_notaries_federal_entity", id.toString())).build();
    }

}
