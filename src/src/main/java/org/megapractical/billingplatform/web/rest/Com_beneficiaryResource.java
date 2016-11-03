package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_beneficiary;
import org.megapractical.billingplatform.service.Com_beneficiaryService;
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
 * REST controller for managing Com_beneficiary.
 */
@RestController
@RequestMapping("/api")
public class Com_beneficiaryResource {

    private final Logger log = LoggerFactory.getLogger(Com_beneficiaryResource.class);
        
    @Inject
    private Com_beneficiaryService com_beneficiaryService;
    
    /**
     * POST  /com-beneficiaries : Create a new com_beneficiary.
     *
     * @param com_beneficiary the com_beneficiary to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_beneficiary, or with status 400 (Bad Request) if the com_beneficiary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-beneficiaries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_beneficiary> createCom_beneficiary(@Valid @RequestBody Com_beneficiary com_beneficiary) throws URISyntaxException {
        log.debug("REST request to save Com_beneficiary : {}", com_beneficiary);
        if (com_beneficiary.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_beneficiary", "idexists", "A new com_beneficiary cannot already have an ID")).body(null);
        }
        Com_beneficiary result = com_beneficiaryService.save(com_beneficiary);
        return ResponseEntity.created(new URI("/api/com-beneficiaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_beneficiary", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-beneficiaries : Updates an existing com_beneficiary.
     *
     * @param com_beneficiary the com_beneficiary to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_beneficiary,
     * or with status 400 (Bad Request) if the com_beneficiary is not valid,
     * or with status 500 (Internal Server Error) if the com_beneficiary couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-beneficiaries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_beneficiary> updateCom_beneficiary(@Valid @RequestBody Com_beneficiary com_beneficiary) throws URISyntaxException {
        log.debug("REST request to update Com_beneficiary : {}", com_beneficiary);
        if (com_beneficiary.getId() == null) {
            return createCom_beneficiary(com_beneficiary);
        }
        Com_beneficiary result = com_beneficiaryService.save(com_beneficiary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_beneficiary", com_beneficiary.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-beneficiaries : get all the com_beneficiaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_beneficiaries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-beneficiaries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_beneficiary>> getAllCom_beneficiaries(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_beneficiaries");
        Page<Com_beneficiary> page = com_beneficiaryService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-beneficiaries");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-beneficiaries/:id : get the "id" com_beneficiary.
     *
     * @param id the id of the com_beneficiary to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_beneficiary, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-beneficiaries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_beneficiary> getCom_beneficiary(@PathVariable Long id) {
        log.debug("REST request to get Com_beneficiary : {}", id);
        Com_beneficiary com_beneficiary = com_beneficiaryService.findOne(id);
        return Optional.ofNullable(com_beneficiary)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-beneficiaries/:id : delete the "id" com_beneficiary.
     *
     * @param id the id of the com_beneficiary to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-beneficiaries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_beneficiary(@PathVariable Long id) {
        log.debug("REST request to delete Com_beneficiary : {}", id);
        com_beneficiaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_beneficiary", id.toString())).build();
    }

}
