package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_taxlegends;
import org.megapractical.billingplatform.service.Com_taxlegendsService;
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
 * REST controller for managing Com_taxlegends.
 */
@RestController
@RequestMapping("/api")
public class Com_taxlegendsResource {

    private final Logger log = LoggerFactory.getLogger(Com_taxlegendsResource.class);
        
    @Inject
    private Com_taxlegendsService com_taxlegendsService;
    
    /**
     * POST  /com-taxlegends : Create a new com_taxlegends.
     *
     * @param com_taxlegends the com_taxlegends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_taxlegends, or with status 400 (Bad Request) if the com_taxlegends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-taxlegends",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxlegends> createCom_taxlegends(@Valid @RequestBody Com_taxlegends com_taxlegends) throws URISyntaxException {
        log.debug("REST request to save Com_taxlegends : {}", com_taxlegends);
        if (com_taxlegends.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_taxlegends", "idexists", "A new com_taxlegends cannot already have an ID")).body(null);
        }
        Com_taxlegends result = com_taxlegendsService.save(com_taxlegends);
        return ResponseEntity.created(new URI("/api/com-taxlegends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_taxlegends", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-taxlegends : Updates an existing com_taxlegends.
     *
     * @param com_taxlegends the com_taxlegends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_taxlegends,
     * or with status 400 (Bad Request) if the com_taxlegends is not valid,
     * or with status 500 (Internal Server Error) if the com_taxlegends couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-taxlegends",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxlegends> updateCom_taxlegends(@Valid @RequestBody Com_taxlegends com_taxlegends) throws URISyntaxException {
        log.debug("REST request to update Com_taxlegends : {}", com_taxlegends);
        if (com_taxlegends.getId() == null) {
            return createCom_taxlegends(com_taxlegends);
        }
        Com_taxlegends result = com_taxlegendsService.save(com_taxlegends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_taxlegends", com_taxlegends.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-taxlegends : get all the com_taxlegends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_taxlegends in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-taxlegends",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_taxlegends>> getAllCom_taxlegends(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_taxlegends");
        Page<Com_taxlegends> page = com_taxlegendsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-taxlegends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-taxlegends/:id : get the "id" com_taxlegends.
     *
     * @param id the id of the com_taxlegends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_taxlegends, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-taxlegends/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_taxlegends> getCom_taxlegends(@PathVariable Long id) {
        log.debug("REST request to get Com_taxlegends : {}", id);
        Com_taxlegends com_taxlegends = com_taxlegendsService.findOne(id);
        return Optional.ofNullable(com_taxlegends)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-taxlegends/:id : delete the "id" com_taxlegends.
     *
     * @param id the id of the com_taxlegends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-taxlegends/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_taxlegends(@PathVariable Long id) {
        log.debug("REST request to delete Com_taxlegends : {}", id);
        com_taxlegendsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_taxlegends", id.toString())).build();
    }

}
