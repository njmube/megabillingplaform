package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_ecc11_transfer;
import org.megapractical.billingplatform.service.Freecom_ecc11_transferService;
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
 * REST controller for managing Freecom_ecc11_transfer.
 */
@RestController
@RequestMapping("/api")
public class Freecom_ecc11_transferResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_ecc11_transferResource.class);
        
    @Inject
    private Freecom_ecc11_transferService freecom_ecc11_transferService;
    
    /**
     * POST  /freecom-ecc-11-transfers : Create a new freecom_ecc11_transfer.
     *
     * @param freecom_ecc11_transfer the freecom_ecc11_transfer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_ecc11_transfer, or with status 400 (Bad Request) if the freecom_ecc11_transfer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-transfers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_transfer> createFreecom_ecc11_transfer(@Valid @RequestBody Freecom_ecc11_transfer freecom_ecc11_transfer) throws URISyntaxException {
        log.debug("REST request to save Freecom_ecc11_transfer : {}", freecom_ecc11_transfer);
        if (freecom_ecc11_transfer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_ecc11_transfer", "idexists", "A new freecom_ecc11_transfer cannot already have an ID")).body(null);
        }
        Freecom_ecc11_transfer result = freecom_ecc11_transferService.save(freecom_ecc11_transfer);
        return ResponseEntity.created(new URI("/api/freecom-ecc-11-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_ecc11_transfer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-ecc-11-transfers : Updates an existing freecom_ecc11_transfer.
     *
     * @param freecom_ecc11_transfer the freecom_ecc11_transfer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_ecc11_transfer,
     * or with status 400 (Bad Request) if the freecom_ecc11_transfer is not valid,
     * or with status 500 (Internal Server Error) if the freecom_ecc11_transfer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-ecc-11-transfers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_transfer> updateFreecom_ecc11_transfer(@Valid @RequestBody Freecom_ecc11_transfer freecom_ecc11_transfer) throws URISyntaxException {
        log.debug("REST request to update Freecom_ecc11_transfer : {}", freecom_ecc11_transfer);
        if (freecom_ecc11_transfer.getId() == null) {
            return createFreecom_ecc11_transfer(freecom_ecc11_transfer);
        }
        Freecom_ecc11_transfer result = freecom_ecc11_transferService.save(freecom_ecc11_transfer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_ecc11_transfer", freecom_ecc11_transfer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-ecc-11-transfers : get all the freecom_ecc11_transfers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_ecc11_transfers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-ecc-11-transfers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Freecom_ecc11_transfer>> getAllFreecom_ecc11_transfers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_ecc11_transfers");
        Page<Freecom_ecc11_transfer> page = freecom_ecc11_transferService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-ecc-11-transfers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /freecom-ecc-11-transfers/:id : get the "id" freecom_ecc11_transfer.
     *
     * @param id the id of the freecom_ecc11_transfer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_ecc11_transfer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-ecc-11-transfers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_ecc11_transfer> getFreecom_ecc11_transfer(@PathVariable Long id) {
        log.debug("REST request to get Freecom_ecc11_transfer : {}", id);
        Freecom_ecc11_transfer freecom_ecc11_transfer = freecom_ecc11_transferService.findOne(id);
        return Optional.ofNullable(freecom_ecc11_transfer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-ecc-11-transfers/:id : delete the "id" freecom_ecc11_transfer.
     *
     * @param id the id of the freecom_ecc11_transfer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-ecc-11-transfers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_ecc11_transfer(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_ecc11_transfer : {}", id);
        freecom_ecc11_transferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_ecc11_transfer", id.toString())).build();
    }

}
