package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_partial_construction_services;
import org.megapractical.billingplatform.service.Com_partial_construction_servicesService;
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
 * REST controller for managing Com_partial_construction_services.
 */
@RestController
@RequestMapping("/api")
public class Com_partial_construction_servicesResource {

    private final Logger log = LoggerFactory.getLogger(Com_partial_construction_servicesResource.class);
        
    @Inject
    private Com_partial_construction_servicesService com_partial_construction_servicesService;
    
    /**
     * POST  /com-partial-construction-services : Create a new com_partial_construction_services.
     *
     * @param com_partial_construction_services the com_partial_construction_services to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_partial_construction_services, or with status 400 (Bad Request) if the com_partial_construction_services has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-partial-construction-services",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_partial_construction_services> createCom_partial_construction_services(@Valid @RequestBody Com_partial_construction_services com_partial_construction_services) throws URISyntaxException {
        log.debug("REST request to save Com_partial_construction_services : {}", com_partial_construction_services);
        if (com_partial_construction_services.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_partial_construction_services", "idexists", "A new com_partial_construction_services cannot already have an ID")).body(null);
        }
        Com_partial_construction_services result = com_partial_construction_servicesService.save(com_partial_construction_services);
        return ResponseEntity.created(new URI("/api/com-partial-construction-services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_partial_construction_services", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-partial-construction-services : Updates an existing com_partial_construction_services.
     *
     * @param com_partial_construction_services the com_partial_construction_services to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_partial_construction_services,
     * or with status 400 (Bad Request) if the com_partial_construction_services is not valid,
     * or with status 500 (Internal Server Error) if the com_partial_construction_services couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-partial-construction-services",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_partial_construction_services> updateCom_partial_construction_services(@Valid @RequestBody Com_partial_construction_services com_partial_construction_services) throws URISyntaxException {
        log.debug("REST request to update Com_partial_construction_services : {}", com_partial_construction_services);
        if (com_partial_construction_services.getId() == null) {
            return createCom_partial_construction_services(com_partial_construction_services);
        }
        Com_partial_construction_services result = com_partial_construction_servicesService.save(com_partial_construction_services);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_partial_construction_services", com_partial_construction_services.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-partial-construction-services : get all the com_partial_construction_services.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_partial_construction_services in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-partial-construction-services",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_partial_construction_services>> getAllCom_partial_construction_services(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_partial_construction_services");
        Page<Com_partial_construction_services> page = com_partial_construction_servicesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-partial-construction-services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-partial-construction-services/:id : get the "id" com_partial_construction_services.
     *
     * @param id the id of the com_partial_construction_services to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_partial_construction_services, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-partial-construction-services/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_partial_construction_services> getCom_partial_construction_services(@PathVariable Long id) {
        log.debug("REST request to get Com_partial_construction_services : {}", id);
        Com_partial_construction_services com_partial_construction_services = com_partial_construction_servicesService.findOne(id);
        return Optional.ofNullable(com_partial_construction_services)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-partial-construction-services/:id : delete the "id" com_partial_construction_services.
     *
     * @param id the id of the com_partial_construction_services to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-partial-construction-services/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_partial_construction_services(@PathVariable Long id) {
        log.debug("REST request to delete Com_partial_construction_services : {}", id);
        com_partial_construction_servicesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_partial_construction_services", id.toString())).build();
    }

}
