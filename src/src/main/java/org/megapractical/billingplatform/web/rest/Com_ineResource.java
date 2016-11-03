package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_ine;
import org.megapractical.billingplatform.service.Com_ineService;
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
 * REST controller for managing Com_ine.
 */
@RestController
@RequestMapping("/api")
public class Com_ineResource {

    private final Logger log = LoggerFactory.getLogger(Com_ineResource.class);
        
    @Inject
    private Com_ineService com_ineService;
    
    /**
     * POST  /com-ines : Create a new com_ine.
     *
     * @param com_ine the com_ine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_ine, or with status 400 (Bad Request) if the com_ine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine> createCom_ine(@Valid @RequestBody Com_ine com_ine) throws URISyntaxException {
        log.debug("REST request to save Com_ine : {}", com_ine);
        if (com_ine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_ine", "idexists", "A new com_ine cannot already have an ID")).body(null);
        }
        Com_ine result = com_ineService.save(com_ine);
        return ResponseEntity.created(new URI("/api/com-ines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_ine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-ines : Updates an existing com_ine.
     *
     * @param com_ine the com_ine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_ine,
     * or with status 400 (Bad Request) if the com_ine is not valid,
     * or with status 500 (Internal Server Error) if the com_ine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine> updateCom_ine(@Valid @RequestBody Com_ine com_ine) throws URISyntaxException {
        log.debug("REST request to update Com_ine : {}", com_ine);
        if (com_ine.getId() == null) {
            return createCom_ine(com_ine);
        }
        Com_ine result = com_ineService.save(com_ine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_ine", com_ine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-ines : get all the com_ines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_ines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-ines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_ine>> getAllCom_ines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_ines");
        Page<Com_ine> page = com_ineService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-ines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-ines/:id : get the "id" com_ine.
     *
     * @param id the id of the com_ine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_ine, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-ines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ine> getCom_ine(@PathVariable Long id) {
        log.debug("REST request to get Com_ine : {}", id);
        Com_ine com_ine = com_ineService.findOne(id);
        return Optional.ofNullable(com_ine)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-ines/:id : delete the "id" com_ine.
     *
     * @param id the id of the com_ine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-ines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_ine(@PathVariable Long id) {
        log.debug("REST request to delete Com_ine : {}", id);
        com_ineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_ine", id.toString())).build();
    }

}
