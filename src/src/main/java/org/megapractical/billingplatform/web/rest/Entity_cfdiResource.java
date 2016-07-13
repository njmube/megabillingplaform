package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Entity_cfdi;
import org.megapractical.billingplatform.service.Entity_cfdiService;
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
 * REST controller for managing Entity_cfdi.
 */
@RestController
@RequestMapping("/api")
public class Entity_cfdiResource {

    private final Logger log = LoggerFactory.getLogger(Entity_cfdiResource.class);
        
    @Inject
    private Entity_cfdiService entity_cfdiService;
    
    /**
     * POST  /entity-cfdis : Create a new entity_cfdi.
     *
     * @param entity_cfdi the entity_cfdi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entity_cfdi, or with status 400 (Bad Request) if the entity_cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entity-cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entity_cfdi> createEntity_cfdi(@RequestBody Entity_cfdi entity_cfdi) throws URISyntaxException {
        log.debug("REST request to save Entity_cfdi : {}", entity_cfdi);
        if (entity_cfdi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("entity_cfdi", "idexists", "A new entity_cfdi cannot already have an ID")).body(null);
        }
        Entity_cfdi result = entity_cfdiService.save(entity_cfdi);
        return ResponseEntity.created(new URI("/api/entity-cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("entity_cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entity-cfdis : Updates an existing entity_cfdi.
     *
     * @param entity_cfdi the entity_cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entity_cfdi,
     * or with status 400 (Bad Request) if the entity_cfdi is not valid,
     * or with status 500 (Internal Server Error) if the entity_cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/entity-cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entity_cfdi> updateEntity_cfdi(@RequestBody Entity_cfdi entity_cfdi) throws URISyntaxException {
        log.debug("REST request to update Entity_cfdi : {}", entity_cfdi);
        if (entity_cfdi.getId() == null) {
            return createEntity_cfdi(entity_cfdi);
        }
        Entity_cfdi result = entity_cfdiService.save(entity_cfdi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("entity_cfdi", entity_cfdi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entity-cfdis : get all the entity_cfdis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of entity_cfdis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/entity-cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Entity_cfdi>> getAllEntity_cfdis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Entity_cfdis");
        Page<Entity_cfdi> page = entity_cfdiService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entity-cfdis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entity-cfdis/:id : get the "id" entity_cfdi.
     *
     * @param id the id of the entity_cfdi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entity_cfdi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/entity-cfdis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Entity_cfdi> getEntity_cfdi(@PathVariable Long id) {
        log.debug("REST request to get Entity_cfdi : {}", id);
        Entity_cfdi entity_cfdi = entity_cfdiService.findOne(id);
        return Optional.ofNullable(entity_cfdi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /entity-cfdis/:id : delete the "id" entity_cfdi.
     *
     * @param id the id of the entity_cfdi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/entity-cfdis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEntity_cfdi(@PathVariable Long id) {
        log.debug("REST request to delete Entity_cfdi : {}", id);
        entity_cfdiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("entity_cfdi", id.toString())).build();
    }

}
