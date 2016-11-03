package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_specific_descriptions;
import org.megapractical.billingplatform.service.Com_specific_descriptionsService;
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
 * REST controller for managing Com_specific_descriptions.
 */
@RestController
@RequestMapping("/api")
public class Com_specific_descriptionsResource {

    private final Logger log = LoggerFactory.getLogger(Com_specific_descriptionsResource.class);
        
    @Inject
    private Com_specific_descriptionsService com_specific_descriptionsService;
    
    /**
     * POST  /com-specific-descriptions : Create a new com_specific_descriptions.
     *
     * @param com_specific_descriptions the com_specific_descriptions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_specific_descriptions, or with status 400 (Bad Request) if the com_specific_descriptions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-specific-descriptions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_specific_descriptions> createCom_specific_descriptions(@Valid @RequestBody Com_specific_descriptions com_specific_descriptions) throws URISyntaxException {
        log.debug("REST request to save Com_specific_descriptions : {}", com_specific_descriptions);
        if (com_specific_descriptions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_specific_descriptions", "idexists", "A new com_specific_descriptions cannot already have an ID")).body(null);
        }
        Com_specific_descriptions result = com_specific_descriptionsService.save(com_specific_descriptions);
        return ResponseEntity.created(new URI("/api/com-specific-descriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_specific_descriptions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-specific-descriptions : Updates an existing com_specific_descriptions.
     *
     * @param com_specific_descriptions the com_specific_descriptions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_specific_descriptions,
     * or with status 400 (Bad Request) if the com_specific_descriptions is not valid,
     * or with status 500 (Internal Server Error) if the com_specific_descriptions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-specific-descriptions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_specific_descriptions> updateCom_specific_descriptions(@Valid @RequestBody Com_specific_descriptions com_specific_descriptions) throws URISyntaxException {
        log.debug("REST request to update Com_specific_descriptions : {}", com_specific_descriptions);
        if (com_specific_descriptions.getId() == null) {
            return createCom_specific_descriptions(com_specific_descriptions);
        }
        Com_specific_descriptions result = com_specific_descriptionsService.save(com_specific_descriptions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_specific_descriptions", com_specific_descriptions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-specific-descriptions : get all the com_specific_descriptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_specific_descriptions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-specific-descriptions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_specific_descriptions>> getAllCom_specific_descriptions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_specific_descriptions");
        Page<Com_specific_descriptions> page = com_specific_descriptionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-specific-descriptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-specific-descriptions/:id : get the "id" com_specific_descriptions.
     *
     * @param id the id of the com_specific_descriptions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_specific_descriptions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-specific-descriptions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_specific_descriptions> getCom_specific_descriptions(@PathVariable Long id) {
        log.debug("REST request to get Com_specific_descriptions : {}", id);
        Com_specific_descriptions com_specific_descriptions = com_specific_descriptionsService.findOne(id);
        return Optional.ofNullable(com_specific_descriptions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-specific-descriptions/:id : delete the "id" com_specific_descriptions.
     *
     * @param id the id of the com_specific_descriptions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-specific-descriptions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_specific_descriptions(@PathVariable Long id) {
        log.debug("REST request to delete Com_specific_descriptions : {}", id);
        com_specific_descriptionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_specific_descriptions", id.toString())).build();
    }

}
