package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_ine_entity;
import org.megapractical.billingplatform.service.Com_ine_entityService;
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
 * REST controller for managing Com_ine_entity.
 */
@RestController
@RequestMapping("/api")
public class Com_ine_entityResource {

    private final Logger log = LoggerFactory.getLogger(Com_ine_entityResource.class);
        
    @Inject
    private Com_ine_entityService com_ine_entityService;
    
    /**
     * POST  /com-ine-entities : Create a new com_ine_entity.
     *
     * @param com_ine_entity the com_ine_entity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_ine_entity, or with status 400 (Bad Request) if the com_ine_entity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ine-entities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine_entity> createCom_ine_entity(@RequestBody Com_ine_entity com_ine_entity) throws URISyntaxException {
        log.debug("REST request to save Com_ine_entity : {}", com_ine_entity);
        if (com_ine_entity.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_ine_entity", "idexists", "A new com_ine_entity cannot already have an ID")).body(null);
        }
        Com_ine_entity result = com_ine_entityService.save(com_ine_entity);
        return ResponseEntity.created(new URI("/api/com-ine-entities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_ine_entity", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-ine-entities : Updates an existing com_ine_entity.
     *
     * @param com_ine_entity the com_ine_entity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_ine_entity,
     * or with status 400 (Bad Request) if the com_ine_entity is not valid,
     * or with status 500 (Internal Server Error) if the com_ine_entity couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ine-entities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine_entity> updateCom_ine_entity(@RequestBody Com_ine_entity com_ine_entity) throws URISyntaxException {
        log.debug("REST request to update Com_ine_entity : {}", com_ine_entity);
        if (com_ine_entity.getId() == null) {
            return createCom_ine_entity(com_ine_entity);
        }
        Com_ine_entity result = com_ine_entityService.save(com_ine_entity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_ine_entity", com_ine_entity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-ine-entities : get all the com_ine_entities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_ine_entities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-ine-entities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_ine_entity>> getAllCom_ine_entities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_ine_entities");
        Page<Com_ine_entity> page = com_ine_entityService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-ine-entities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-ine-entities/:id : get the "id" com_ine_entity.
     *
     * @param id the id of the com_ine_entity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_ine_entity, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-ine-entities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine_entity> getCom_ine_entity(@PathVariable Long id) {
        log.debug("REST request to get Com_ine_entity : {}", id);
        Com_ine_entity com_ine_entity = com_ine_entityService.findOne(id);
        return Optional.ofNullable(com_ine_entity)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-ine-entities/:id : delete the "id" com_ine_entity.
     *
     * @param id the id of the com_ine_entity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-ine-entities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_ine_entity(@PathVariable Long id) {
        log.debug("REST request to delete Com_ine_entity : {}", id);
        com_ine_entityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_ine_entity", id.toString())).build();
    }

}
