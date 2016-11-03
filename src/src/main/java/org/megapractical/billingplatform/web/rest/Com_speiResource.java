package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_spei;
import org.megapractical.billingplatform.service.Com_speiService;
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
 * REST controller for managing Com_spei.
 */
@RestController
@RequestMapping("/api")
public class Com_speiResource {

    private final Logger log = LoggerFactory.getLogger(Com_speiResource.class);
        
    @Inject
    private Com_speiService com_speiService;
    
    /**
     * POST  /com-speis : Create a new com_spei.
     *
     * @param com_spei the com_spei to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_spei, or with status 400 (Bad Request) if the com_spei has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-speis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei> createCom_spei(@Valid @RequestBody Com_spei com_spei) throws URISyntaxException {
        log.debug("REST request to save Com_spei : {}", com_spei);
        if (com_spei.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_spei", "idexists", "A new com_spei cannot already have an ID")).body(null);
        }
        Com_spei result = com_speiService.save(com_spei);
        return ResponseEntity.created(new URI("/api/com-speis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_spei", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-speis : Updates an existing com_spei.
     *
     * @param com_spei the com_spei to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_spei,
     * or with status 400 (Bad Request) if the com_spei is not valid,
     * or with status 500 (Internal Server Error) if the com_spei couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-speis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei> updateCom_spei(@Valid @RequestBody Com_spei com_spei) throws URISyntaxException {
        log.debug("REST request to update Com_spei : {}", com_spei);
        if (com_spei.getId() == null) {
            return createCom_spei(com_spei);
        }
        Com_spei result = com_speiService.save(com_spei);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_spei", com_spei.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-speis : get all the com_speis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_speis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-speis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_spei>> getAllCom_speis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_speis");
        Page<Com_spei> page = com_speiService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-speis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-speis/:id : get the "id" com_spei.
     *
     * @param id the id of the com_spei to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_spei, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-speis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_spei> getCom_spei(@PathVariable Long id) {
        log.debug("REST request to get Com_spei : {}", id);
        Com_spei com_spei = com_speiService.findOne(id);
        return Optional.ofNullable(com_spei)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-speis/:id : delete the "id" com_spei.
     *
     * @param id the id of the com_spei to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-speis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_spei(@PathVariable Long id) {
        log.debug("REST request to delete Com_spei : {}", id);
        com_speiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_spei", id.toString())).build();
    }

}
