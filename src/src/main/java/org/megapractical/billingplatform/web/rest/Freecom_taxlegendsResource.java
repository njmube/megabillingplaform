package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_taxlegends;
import org.megapractical.billingplatform.service.Freecom_taxlegendsService;
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
 * REST controller for managing Freecom_taxlegends.
 */
@RestController
@RequestMapping("/api")
public class Freecom_taxlegendsResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_taxlegendsResource.class);
        
    @Inject
    private Freecom_taxlegendsService freecom_taxlegendsService;
    
    /**
     * POST  /freecom-taxlegends : Create a new freecom_taxlegends.
     *
     * @param freecom_taxlegends the freecom_taxlegends to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_taxlegends, or with status 400 (Bad Request) if the freecom_taxlegends has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-taxlegends",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxlegends> createFreecom_taxlegends(@Valid @RequestBody Freecom_taxlegends freecom_taxlegends) throws URISyntaxException {
        log.debug("REST request to save Freecom_taxlegends : {}", freecom_taxlegends);
        if (freecom_taxlegends.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_taxlegends", "idexists", "A new freecom_taxlegends cannot already have an ID")).body(null);
        }
        Freecom_taxlegends result = freecom_taxlegendsService.save(freecom_taxlegends);
        return ResponseEntity.created(new URI("/api/freecom-taxlegends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_taxlegends", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-taxlegends : Updates an existing freecom_taxlegends.
     *
     * @param freecom_taxlegends the freecom_taxlegends to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_taxlegends,
     * or with status 400 (Bad Request) if the freecom_taxlegends is not valid,
     * or with status 500 (Internal Server Error) if the freecom_taxlegends couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-taxlegends",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxlegends> updateFreecom_taxlegends(@Valid @RequestBody Freecom_taxlegends freecom_taxlegends) throws URISyntaxException {
        log.debug("REST request to update Freecom_taxlegends : {}", freecom_taxlegends);
        if (freecom_taxlegends.getId() == null) {
            return createFreecom_taxlegends(freecom_taxlegends);
        }
        Freecom_taxlegends result = freecom_taxlegendsService.save(freecom_taxlegends);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_taxlegends", freecom_taxlegends.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-taxlegends : get all the freecom_taxlegends.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_taxlegends in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-taxlegends",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_taxlegends>> getAllFreecom_taxlegends(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_taxlegends");
        Page<Freecom_taxlegends> page = freecom_taxlegendsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-taxlegends");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-taxlegends/:id : get the "id" freecom_taxlegends.
     *
     * @param id the id of the freecom_taxlegends to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_taxlegends, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-taxlegends/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_taxlegends> getFreecom_taxlegends(@PathVariable Long id) {
        log.debug("REST request to get Freecom_taxlegends : {}", id);
        Freecom_taxlegends freecom_taxlegends = freecom_taxlegendsService.findOne(id);
        return Optional.ofNullable(freecom_taxlegends)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-taxlegends/:id : delete the "id" freecom_taxlegends.
     *
     * @param id the id of the freecom_taxlegends to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-taxlegends/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_taxlegends(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_taxlegends : {}", id);
        freecom_taxlegendsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_taxlegends", id.toString())).build();
    }

}
