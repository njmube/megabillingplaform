package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_partial_construction_services;
import org.megapractical.billingplatform.service.Freecom_partial_construction_servicesService;
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
 * REST controller for managing Freecom_partial_construction_services.
 */
@RestController
@RequestMapping("/api")
public class Freecom_partial_construction_servicesResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_partial_construction_servicesResource.class);
        
    @Inject
    private Freecom_partial_construction_servicesService freecom_partial_construction_servicesService;
    
    /**
     * POST  /freecom-partial-construction-services : Create a new freecom_partial_construction_services.
     *
     * @param freecom_partial_construction_services the freecom_partial_construction_services to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_partial_construction_services, or with status 400 (Bad Request) if the freecom_partial_construction_services has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-partial-construction-services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_partial_construction_services> createFreecom_partial_construction_services(@Valid @RequestBody Freecom_partial_construction_services freecom_partial_construction_services) throws URISyntaxException {
        log.debug("REST request to save Freecom_partial_construction_services : {}", freecom_partial_construction_services);
        if (freecom_partial_construction_services.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_partial_construction_services", "idexists", "A new freecom_partial_construction_services cannot already have an ID")).body(null);
        }
        Freecom_partial_construction_services result = freecom_partial_construction_servicesService.save(freecom_partial_construction_services);
        return ResponseEntity.created(new URI("/api/freecom-partial-construction-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_partial_construction_services", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-partial-construction-services : Updates an existing freecom_partial_construction_services.
     *
     * @param freecom_partial_construction_services the freecom_partial_construction_services to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_partial_construction_services,
     * or with status 400 (Bad Request) if the freecom_partial_construction_services is not valid,
     * or with status 500 (Internal Server Error) if the freecom_partial_construction_services couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-partial-construction-services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_partial_construction_services> updateFreecom_partial_construction_services(@Valid @RequestBody Freecom_partial_construction_services freecom_partial_construction_services) throws URISyntaxException {
        log.debug("REST request to update Freecom_partial_construction_services : {}", freecom_partial_construction_services);
        if (freecom_partial_construction_services.getId() == null) {
            return createFreecom_partial_construction_services(freecom_partial_construction_services);
        }
        Freecom_partial_construction_services result = freecom_partial_construction_servicesService.save(freecom_partial_construction_services);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_partial_construction_services", freecom_partial_construction_services.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-partial-construction-services : get all the freecom_partial_construction_services.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_partial_construction_services in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-partial-construction-services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_partial_construction_services>> getAllFreecom_partial_construction_services(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_partial_construction_services");
        Page<Freecom_partial_construction_services> page = freecom_partial_construction_servicesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-partial-construction-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-partial-construction-services/:id : get the "id" freecom_partial_construction_services.
     *
     * @param id the id of the freecom_partial_construction_services to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_partial_construction_services, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-partial-construction-services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_partial_construction_services> getFreecom_partial_construction_services(@PathVariable Long id) {
        log.debug("REST request to get Freecom_partial_construction_services : {}", id);
        Freecom_partial_construction_services freecom_partial_construction_services = freecom_partial_construction_servicesService.findOne(id);
        return Optional.ofNullable(freecom_partial_construction_services)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-partial-construction-services/:id : delete the "id" freecom_partial_construction_services.
     *
     * @param id the id of the freecom_partial_construction_services to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-partial-construction-services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_partial_construction_services(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_partial_construction_services : {}", id);
        freecom_partial_construction_servicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_partial_construction_services", id.toString())).build();
    }

}
