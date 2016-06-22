package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Cfdi_types;
import org.megapractical.billingplatform.service.Cfdi_typesService;
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
 * REST controller for managing Cfdi_types.
 */
@RestController
@RequestMapping("/api")
public class Cfdi_typesResource {

    private final Logger log = LoggerFactory.getLogger(Cfdi_typesResource.class);
        
    @Inject
    private Cfdi_typesService cfdi_typesService;
    
    /**
     * POST  /cfdi-types : Create a new cfdi_types.
     *
     * @param cfdi_types the cfdi_types to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi_types, or with status 400 (Bad Request) if the cfdi_types has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_types> createCfdi_types(@Valid @RequestBody Cfdi_types cfdi_types) throws URISyntaxException {
        log.debug("REST request to save Cfdi_types : {}", cfdi_types);
        if (cfdi_types.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi_types", "idexists", "A new cfdi_types cannot already have an ID")).body(null);
        }
        Cfdi_types result = cfdi_typesService.save(cfdi_types);
        return ResponseEntity.created(new URI("/api/cfdi-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi_types", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdi-types : Updates an existing cfdi_types.
     *
     * @param cfdi_types the cfdi_types to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi_types,
     * or with status 400 (Bad Request) if the cfdi_types is not valid,
     * or with status 500 (Internal Server Error) if the cfdi_types couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_types> updateCfdi_types(@Valid @RequestBody Cfdi_types cfdi_types) throws URISyntaxException {
        log.debug("REST request to update Cfdi_types : {}", cfdi_types);
        if (cfdi_types.getId() == null) {
            return createCfdi_types(cfdi_types);
        }
        Cfdi_types result = cfdi_typesService.save(cfdi_types);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi_types", cfdi_types.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdi-types : get all the cfdi_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdi_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdi-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cfdi_types>> getAllCfdi_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdi_types");
        Page<Cfdi_types> page = cfdi_typesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdi-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cfdi-types/:id : get the "id" cfdi_types.
     *
     * @param id the id of the cfdi_types to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi_types, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdi-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_types> getCfdi_types(@PathVariable Long id) {
        log.debug("REST request to get Cfdi_types : {}", id);
        Cfdi_types cfdi_types = cfdi_typesService.findOne(id);
        return Optional.ofNullable(cfdi_types)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdi-types/:id : delete the "id" cfdi_types.
     *
     * @param id the id of the cfdi_types to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdi-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi_types(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi_types : {}", id);
        cfdi_typesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi_types", id.toString())).build();
    }

}
