package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_determined;
import org.megapractical.billingplatform.service.Com_determinedService;
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
 * REST controller for managing Com_determined.
 */
@RestController
@RequestMapping("/api")
public class Com_determinedResource {

    private final Logger log = LoggerFactory.getLogger(Com_determinedResource.class);
        
    @Inject
    private Com_determinedService com_determinedService;
    
    /**
     * POST  /com-determineds : Create a new com_determined.
     *
     * @param com_determined the com_determined to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_determined, or with status 400 (Bad Request) if the com_determined has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-determineds",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_determined> createCom_determined(@Valid @RequestBody Com_determined com_determined) throws URISyntaxException {
        log.debug("REST request to save Com_determined : {}", com_determined);
        if (com_determined.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_determined", "idexists", "A new com_determined cannot already have an ID")).body(null);
        }
        Com_determined result = com_determinedService.save(com_determined);
        return ResponseEntity.created(new URI("/api/com-determineds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_determined", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-determineds : Updates an existing com_determined.
     *
     * @param com_determined the com_determined to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_determined,
     * or with status 400 (Bad Request) if the com_determined is not valid,
     * or with status 500 (Internal Server Error) if the com_determined couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-determineds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_determined> updateCom_determined(@Valid @RequestBody Com_determined com_determined) throws URISyntaxException {
        log.debug("REST request to update Com_determined : {}", com_determined);
        if (com_determined.getId() == null) {
            return createCom_determined(com_determined);
        }
        Com_determined result = com_determinedService.save(com_determined);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_determined", com_determined.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-determineds : get all the com_determineds.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_determineds in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-determineds",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_determined>> getAllCom_determineds(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_determineds");
        Page<Com_determined> page = com_determinedService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-determineds");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-determineds/:id : get the "id" com_determined.
     *
     * @param id the id of the com_determined to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_determined, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-determineds/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_determined> getCom_determined(@PathVariable Long id) {
        log.debug("REST request to get Com_determined : {}", id);
        Com_determined com_determined = com_determinedService.findOne(id);
        return Optional.ofNullable(com_determined)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-determineds/:id : delete the "id" com_determined.
     *
     * @param id the id of the com_determined to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-determineds/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_determined(@PathVariable Long id) {
        log.debug("REST request to delete Com_determined : {}", id);
        com_determinedService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_determined", id.toString())).build();
    }

}
