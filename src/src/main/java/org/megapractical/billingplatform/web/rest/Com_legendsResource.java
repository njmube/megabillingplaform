package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_legends;
import org.megapractical.billingplatform.service.Com_legendsService;
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
 * REST controller for managing Com_legends.
 */
@RestController
@RequestMapping("/api")
public class Com_legendsResource {

    private final Logger log = LoggerFactory.getLogger(Com_legendsResource.class);
        
    @Inject
    private Com_legendsService com_legendsService;
    
    /**
     * POST  /com-legends : Create a new com_legends.
     *
     * @param com_legends the com_legends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_legends, or with status 400 (Bad Request) if the com_legends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-legends",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_legends> createCom_legends(@Valid @RequestBody Com_legends com_legends) throws URISyntaxException {
        log.debug("REST request to save Com_legends : {}", com_legends);
        if (com_legends.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_legends", "idexists", "A new com_legends cannot already have an ID")).body(null);
        }
        Com_legends result = com_legendsService.save(com_legends);
        return ResponseEntity.created(new URI("/api/com-legends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_legends", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-legends : Updates an existing com_legends.
     *
     * @param com_legends the com_legends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_legends,
     * or with status 400 (Bad Request) if the com_legends is not valid,
     * or with status 500 (Internal Server Error) if the com_legends couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-legends",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_legends> updateCom_legends(@Valid @RequestBody Com_legends com_legends) throws URISyntaxException {
        log.debug("REST request to update Com_legends : {}", com_legends);
        if (com_legends.getId() == null) {
            return createCom_legends(com_legends);
        }
        Com_legends result = com_legendsService.save(com_legends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_legends", com_legends.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-legends : get all the com_legends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_legends in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-legends",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_legends>> getAllCom_legends(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_legends");
        Page<Com_legends> page = com_legendsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-legends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-legends/:id : get the "id" com_legends.
     *
     * @param id the id of the com_legends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_legends, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-legends/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_legends> getCom_legends(@PathVariable Long id) {
        log.debug("REST request to get Com_legends : {}", id);
        Com_legends com_legends = com_legendsService.findOne(id);
        return Optional.ofNullable(com_legends)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-legends/:id : delete the "id" com_legends.
     *
     * @param id the id of the com_legends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-legends/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_legends(@PathVariable Long id) {
        log.debug("REST request to delete Com_legends : {}", id);
        com_legendsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_legends", id.toString())).build();
    }

}
