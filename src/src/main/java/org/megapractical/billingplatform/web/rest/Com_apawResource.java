package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_apaw;
import org.megapractical.billingplatform.service.Com_apawService;
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
 * REST controller for managing Com_apaw.
 */
@RestController
@RequestMapping("/api")
public class Com_apawResource {

    private final Logger log = LoggerFactory.getLogger(Com_apawResource.class);
        
    @Inject
    private Com_apawService com_apawService;
    
    /**
     * POST  /com-apaws : Create a new com_apaw.
     *
     * @param com_apaw the com_apaw to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_apaw, or with status 400 (Bad Request) if the com_apaw has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-apaws",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_apaw> createCom_apaw(@Valid @RequestBody Com_apaw com_apaw) throws URISyntaxException {
        log.debug("REST request to save Com_apaw : {}", com_apaw);
        if (com_apaw.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_apaw", "idexists", "A new com_apaw cannot already have an ID")).body(null);
        }
        Com_apaw result = com_apawService.save(com_apaw);
        return ResponseEntity.created(new URI("/api/com-apaws/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_apaw", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-apaws : Updates an existing com_apaw.
     *
     * @param com_apaw the com_apaw to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_apaw,
     * or with status 400 (Bad Request) if the com_apaw is not valid,
     * or with status 500 (Internal Server Error) if the com_apaw couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-apaws",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_apaw> updateCom_apaw(@Valid @RequestBody Com_apaw com_apaw) throws URISyntaxException {
        log.debug("REST request to update Com_apaw : {}", com_apaw);
        if (com_apaw.getId() == null) {
            return createCom_apaw(com_apaw);
        }
        Com_apaw result = com_apawService.save(com_apaw);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_apaw", com_apaw.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-apaws : get all the com_apaws.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_apaws in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-apaws",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_apaw>> getAllCom_apaws(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_apaws");
        Page<Com_apaw> page = com_apawService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-apaws");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-apaws/:id : get the "id" com_apaw.
     *
     * @param id the id of the com_apaw to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_apaw, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-apaws/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_apaw> getCom_apaw(@PathVariable Long id) {
        log.debug("REST request to get Com_apaw : {}", id);
        Com_apaw com_apaw = com_apawService.findOne(id);
        return Optional.ofNullable(com_apaw)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-apaws/:id : delete the "id" com_apaw.
     *
     * @param id the id of the com_apaw to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-apaws/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_apaw(@PathVariable Long id) {
        log.debug("REST request to delete Com_apaw : {}", id);
        com_apawService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_apaw", id.toString())).build();
    }

}
