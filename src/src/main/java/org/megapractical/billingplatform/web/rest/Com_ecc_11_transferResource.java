package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_ecc_11_transfer;
import org.megapractical.billingplatform.service.Com_ecc_11_transferService;
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
 * REST controller for managing Com_ecc_11_transfer.
 */
@RestController
@RequestMapping("/api")
public class Com_ecc_11_transferResource {

    private final Logger log = LoggerFactory.getLogger(Com_ecc_11_transferResource.class);
        
    @Inject
    private Com_ecc_11_transferService com_ecc_11_transferService;
    
    /**
     * POST  /com-ecc-11-transfers : Create a new com_ecc_11_transfer.
     *
     * @param com_ecc_11_transfer the com_ecc_11_transfer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_ecc_11_transfer, or with status 400 (Bad Request) if the com_ecc_11_transfer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-transfers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_transfer> createCom_ecc_11_transfer(@Valid @RequestBody Com_ecc_11_transfer com_ecc_11_transfer) throws URISyntaxException {
        log.debug("REST request to save Com_ecc_11_transfer : {}", com_ecc_11_transfer);
        if (com_ecc_11_transfer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_ecc_11_transfer", "idexists", "A new com_ecc_11_transfer cannot already have an ID")).body(null);
        }
        Com_ecc_11_transfer result = com_ecc_11_transferService.save(com_ecc_11_transfer);
        return ResponseEntity.created(new URI("/api/com-ecc-11-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_ecc_11_transfer", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-ecc-11-transfers : Updates an existing com_ecc_11_transfer.
     *
     * @param com_ecc_11_transfer the com_ecc_11_transfer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_ecc_11_transfer,
     * or with status 400 (Bad Request) if the com_ecc_11_transfer is not valid,
     * or with status 500 (Internal Server Error) if the com_ecc_11_transfer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-ecc-11-transfers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_transfer> updateCom_ecc_11_transfer(@Valid @RequestBody Com_ecc_11_transfer com_ecc_11_transfer) throws URISyntaxException {
        log.debug("REST request to update Com_ecc_11_transfer : {}", com_ecc_11_transfer);
        if (com_ecc_11_transfer.getId() == null) {
            return createCom_ecc_11_transfer(com_ecc_11_transfer);
        }
        Com_ecc_11_transfer result = com_ecc_11_transferService.save(com_ecc_11_transfer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_ecc_11_transfer", com_ecc_11_transfer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-ecc-11-transfers : get all the com_ecc_11_transfers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_ecc_11_transfers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-ecc-11-transfers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Com_ecc_11_transfer>> getAllCom_ecc_11_transfers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_ecc_11_transfers");
        Page<Com_ecc_11_transfer> page = com_ecc_11_transferService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-ecc-11-transfers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /com-ecc-11-transfers/:id : get the "id" com_ecc_11_transfer.
     *
     * @param id the id of the com_ecc_11_transfer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_ecc_11_transfer, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-ecc-11-transfers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_ecc_11_transfer> getCom_ecc_11_transfer(@PathVariable Long id) {
        log.debug("REST request to get Com_ecc_11_transfer : {}", id);
        Com_ecc_11_transfer com_ecc_11_transfer = com_ecc_11_transferService.findOne(id);
        return Optional.ofNullable(com_ecc_11_transfer)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-ecc-11-transfers/:id : delete the "id" com_ecc_11_transfer.
     *
     * @param id the id of the com_ecc_11_transfer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-ecc-11-transfers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_ecc_11_transfer(@PathVariable Long id) {
        log.debug("REST request to delete Com_ecc_11_transfer : {}", id);
        com_ecc_11_transferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_ecc_11_transfer", id.toString())).build();
    }

}
