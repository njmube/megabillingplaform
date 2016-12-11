package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_pn_federal_entity;
import org.megapractical.billingplatform.service.C_pn_federal_entityService;
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
 * REST controller for managing C_pn_federal_entity.
 */
@RestController
@RequestMapping("/api")
public class C_pn_federal_entityResource {

    private final Logger log = LoggerFactory.getLogger(C_pn_federal_entityResource.class);
        
    @Inject
    private C_pn_federal_entityService c_pn_federal_entityService;
    
    /**
     * POST  /c-pn-federal-entities : Create a new c_pn_federal_entity.
     *
     * @param c_pn_federal_entity the c_pn_federal_entity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_pn_federal_entity, or with status 400 (Bad Request) if the c_pn_federal_entity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-pn-federal-entities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_pn_federal_entity> createC_pn_federal_entity(@Valid @RequestBody C_pn_federal_entity c_pn_federal_entity) throws URISyntaxException {
        log.debug("REST request to save C_pn_federal_entity : {}", c_pn_federal_entity);
        if (c_pn_federal_entity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_pn_federal_entity", "idexists", "A new c_pn_federal_entity cannot already have an ID")).body(null);
        }
        C_pn_federal_entity result = c_pn_federal_entityService.save(c_pn_federal_entity);
        return ResponseEntity.created(new URI("/api/c-pn-federal-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_pn_federal_entity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-pn-federal-entities : Updates an existing c_pn_federal_entity.
     *
     * @param c_pn_federal_entity the c_pn_federal_entity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_pn_federal_entity,
     * or with status 400 (Bad Request) if the c_pn_federal_entity is not valid,
     * or with status 500 (Internal Server Error) if the c_pn_federal_entity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-pn-federal-entities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_pn_federal_entity> updateC_pn_federal_entity(@Valid @RequestBody C_pn_federal_entity c_pn_federal_entity) throws URISyntaxException {
        log.debug("REST request to update C_pn_federal_entity : {}", c_pn_federal_entity);
        if (c_pn_federal_entity.getId() == null) {
            return createC_pn_federal_entity(c_pn_federal_entity);
        }
        C_pn_federal_entity result = c_pn_federal_entityService.save(c_pn_federal_entity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_pn_federal_entity", c_pn_federal_entity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-pn-federal-entities : get all the c_pn_federal_entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_pn_federal_entities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-pn-federal-entities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_pn_federal_entity>> getAllC_pn_federal_entities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_pn_federal_entities");
        Page<C_pn_federal_entity> page = c_pn_federal_entityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-pn-federal-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-pn-federal-entities/:id : get the "id" c_pn_federal_entity.
     *
     * @param id the id of the c_pn_federal_entity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_pn_federal_entity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-pn-federal-entities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_pn_federal_entity> getC_pn_federal_entity(@PathVariable Long id) {
        log.debug("REST request to get C_pn_federal_entity : {}", id);
        C_pn_federal_entity c_pn_federal_entity = c_pn_federal_entityService.findOne(id);
        return Optional.ofNullable(c_pn_federal_entity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-pn-federal-entities/:id : delete the "id" c_pn_federal_entity.
     *
     * @param id the id of the c_pn_federal_entity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-pn-federal-entities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_pn_federal_entity(@PathVariable Long id) {
        log.debug("REST request to delete C_pn_federal_entity : {}", id);
        c_pn_federal_entityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_pn_federal_entity", id.toString())).build();
    }

}
