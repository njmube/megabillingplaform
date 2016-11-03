package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_taxregistration;
import org.megapractical.billingplatform.service.Com_taxregistrationService;
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
 * REST controller for managing Com_taxregistration.
 */
@RestController
@RequestMapping("/api")
public class Com_taxregistrationResource {

    private final Logger log = LoggerFactory.getLogger(Com_taxregistrationResource.class);
        
    @Inject
    private Com_taxregistrationService com_taxregistrationService;
    
    /**
     * POST  /com-taxregistrations : Create a new com_taxregistration.
     *
     * @param com_taxregistration the com_taxregistration to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_taxregistration, or with status 400 (Bad Request) if the com_taxregistration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-taxregistrations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxregistration> createCom_taxregistration(@Valid @RequestBody Com_taxregistration com_taxregistration) throws URISyntaxException {
        log.debug("REST request to save Com_taxregistration : {}", com_taxregistration);
        if (com_taxregistration.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_taxregistration", "idexists", "A new com_taxregistration cannot already have an ID")).body(null);
        }
        Com_taxregistration result = com_taxregistrationService.save(com_taxregistration);
        return ResponseEntity.created(new URI("/api/com-taxregistrations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_taxregistration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-taxregistrations : Updates an existing com_taxregistration.
     *
     * @param com_taxregistration the com_taxregistration to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_taxregistration,
     * or with status 400 (Bad Request) if the com_taxregistration is not valid,
     * or with status 500 (Internal Server Error) if the com_taxregistration couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-taxregistrations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxregistration> updateCom_taxregistration(@Valid @RequestBody Com_taxregistration com_taxregistration) throws URISyntaxException {
        log.debug("REST request to update Com_taxregistration : {}", com_taxregistration);
        if (com_taxregistration.getId() == null) {
            return createCom_taxregistration(com_taxregistration);
        }
        Com_taxregistration result = com_taxregistrationService.save(com_taxregistration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_taxregistration", com_taxregistration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-taxregistrations : get all the com_taxregistrations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_taxregistrations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-taxregistrations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_taxregistration>> getAllCom_taxregistrations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_taxregistrations");
        Page<Com_taxregistration> page = com_taxregistrationService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-taxregistrations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-taxregistrations/:id : get the "id" com_taxregistration.
     *
     * @param id the id of the com_taxregistration to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_taxregistration, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-taxregistrations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxregistration> getCom_taxregistration(@PathVariable Long id) {
        log.debug("REST request to get Com_taxregistration : {}", id);
        Com_taxregistration com_taxregistration = com_taxregistrationService.findOne(id);
        return Optional.ofNullable(com_taxregistration)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-taxregistrations/:id : delete the "id" com_taxregistration.
     *
     * @param id the id of the com_taxregistration to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-taxregistrations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_taxregistration(@PathVariable Long id) {
        log.debug("REST request to delete Com_taxregistration : {}", id);
        com_taxregistrationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_taxregistration", id.toString())).build();
    }

}
