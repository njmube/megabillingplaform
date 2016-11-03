package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_local_taxes;
import org.megapractical.billingplatform.service.Com_local_taxesService;
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
 * REST controller for managing Com_local_taxes.
 */
@RestController
@RequestMapping("/api")
public class Com_local_taxesResource {

    private final Logger log = LoggerFactory.getLogger(Com_local_taxesResource.class);
        
    @Inject
    private Com_local_taxesService com_local_taxesService;
    
    /**
     * POST  /com-local-taxes : Create a new com_local_taxes.
     *
     * @param com_local_taxes the com_local_taxes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_local_taxes, or with status 400 (Bad Request) if the com_local_taxes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-taxes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_taxes> createCom_local_taxes(@Valid @RequestBody Com_local_taxes com_local_taxes) throws URISyntaxException {
        log.debug("REST request to save Com_local_taxes : {}", com_local_taxes);
        if (com_local_taxes.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_local_taxes", "idexists", "A new com_local_taxes cannot already have an ID")).body(null);
        }
        Com_local_taxes result = com_local_taxesService.save(com_local_taxes);
        return ResponseEntity.created(new URI("/api/com-local-taxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_local_taxes", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-local-taxes : Updates an existing com_local_taxes.
     *
     * @param com_local_taxes the com_local_taxes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_local_taxes,
     * or with status 400 (Bad Request) if the com_local_taxes is not valid,
     * or with status 500 (Internal Server Error) if the com_local_taxes couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-local-taxes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_taxes> updateCom_local_taxes(@Valid @RequestBody Com_local_taxes com_local_taxes) throws URISyntaxException {
        log.debug("REST request to update Com_local_taxes : {}", com_local_taxes);
        if (com_local_taxes.getId() == null) {
            return createCom_local_taxes(com_local_taxes);
        }
        Com_local_taxes result = com_local_taxesService.save(com_local_taxes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_local_taxes", com_local_taxes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-local-taxes : get all the com_local_taxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_local_taxes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-local-taxes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_local_taxes>> getAllCom_local_taxes(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_local_taxes");
        Page<Com_local_taxes> page = com_local_taxesService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-local-taxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-local-taxes/:id : get the "id" com_local_taxes.
     *
     * @param id the id of the com_local_taxes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_local_taxes, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-local-taxes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_local_taxes> getCom_local_taxes(@PathVariable Long id) {
        log.debug("REST request to get Com_local_taxes : {}", id);
        Com_local_taxes com_local_taxes = com_local_taxesService.findOne(id);
        return Optional.ofNullable(com_local_taxes)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-local-taxes/:id : delete the "id" com_local_taxes.
     *
     * @param id the id of the com_local_taxes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-local-taxes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_local_taxes(@PathVariable Long id) {
        log.debug("REST request to delete Com_local_taxes : {}", id);
        com_local_taxesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_local_taxes", id.toString())).build();
    }

}
