package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Services;
import org.megapractical.billingplatform.service.ServicesService;
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
 * REST controller for managing Services.
 */
@RestController
@RequestMapping("/api")
public class ServicesResource {

    private final Logger log = LoggerFactory.getLogger(ServicesResource.class);
        
    @Inject
    private ServicesService servicesService;
    
    /**
     * POST  /services : Create a new services.
     *
     * @param services the services to create
     * @return the ResponseEntity with status 201 (Created) and with body the new services, or with status 400 (Bad Request) if the services has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> createServices(@RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to save Services : {}", services);
        if (services.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("services", "idexists", "A new services cannot already have an ID")).body(null);
        }
        Services result = servicesService.save(services);
        return ResponseEntity.created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("services", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /services : Updates an existing services.
     *
     * @param services the services to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated services,
     * or with status 400 (Bad Request) if the services is not valid,
     * or with status 500 (Internal Server Error) if the services couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> updateServices(@RequestBody Services services) throws URISyntaxException {
        log.debug("REST request to update Services : {}", services);
        if (services.getId() == null) {
            return createServices(services);
        }
        Services result = servicesService.save(services);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("services", services.getId().toString()))
            .body(result);
    }

    /**
     * GET  /services : get all the services.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of services in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Services>> getAllServices(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Services");
        Page<Services> page = servicesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /services/:id : get the "id" services.
     *
     * @param id the id of the services to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the services, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Services> getServices(@PathVariable Long id) {
        log.debug("REST request to get Services : {}", id);
        Services services = servicesService.findOne(id);
        return Optional.ofNullable(services)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /services/:id : delete the "id" services.
     *
     * @param id the id of the services to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        log.debug("REST request to delete Services : {}", id);
        servicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("services", id.toString())).build();
    }

}
