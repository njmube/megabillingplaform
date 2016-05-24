package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Voucher_type;
import org.megapractical.billingplatform.service.Voucher_typeService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Voucher_type.
 */
@RestController
@RequestMapping("/api")
public class Voucher_typeResource {

    private final Logger log = LoggerFactory.getLogger(Voucher_typeResource.class);
        
    @Inject
    private Voucher_typeService voucher_typeService;
    
    /**
     * POST  /voucher-types : Create a new voucher_type.
     *
     * @param voucher_type the voucher_type to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voucher_type, or with status 400 (Bad Request) if the voucher_type has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/voucher-types",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voucher_type> createVoucher_type(@RequestBody Voucher_type voucher_type) throws URISyntaxException {
        log.debug("REST request to save Voucher_type : {}", voucher_type);
        if (voucher_type.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("voucher_type", "idexists", "A new voucher_type cannot already have an ID")).body(null);
        }
        Voucher_type result = voucher_typeService.save(voucher_type);
        return ResponseEntity.created(new URI("/api/voucher-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("voucher_type", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voucher-types : Updates an existing voucher_type.
     *
     * @param voucher_type the voucher_type to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voucher_type,
     * or with status 400 (Bad Request) if the voucher_type is not valid,
     * or with status 500 (Internal Server Error) if the voucher_type couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/voucher-types",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voucher_type> updateVoucher_type(@RequestBody Voucher_type voucher_type) throws URISyntaxException {
        log.debug("REST request to update Voucher_type : {}", voucher_type);
        if (voucher_type.getId() == null) {
            return createVoucher_type(voucher_type);
        }
        Voucher_type result = voucher_typeService.save(voucher_type);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("voucher_type", voucher_type.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voucher-types : get all the voucher_types.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of voucher_types in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/voucher-types",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Voucher_type>> getAllVoucher_types(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Voucher_types");
        Page<Voucher_type> page = voucher_typeService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/voucher-types");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /voucher-types/:id : get the "id" voucher_type.
     *
     * @param id the id of the voucher_type to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voucher_type, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/voucher-types/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Voucher_type> getVoucher_type(@PathVariable Long id) {
        log.debug("REST request to get Voucher_type : {}", id);
        Voucher_type voucher_type = voucher_typeService.findOne(id);
        return Optional.ofNullable(voucher_type)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /voucher-types/:id : delete the "id" voucher_type.
     *
     * @param id the id of the voucher_type to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/voucher-types/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVoucher_type(@PathVariable Long id) {
        log.debug("REST request to delete Voucher_type : {}", id);
        voucher_typeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("voucher_type", id.toString())).build();
    }

}
