package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_address_request;
import org.megapractical.billingplatform.service.Tax_address_requestService;
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
 * REST controller for managing Tax_address_request.
 */
@RestController
@RequestMapping("/api")
public class Tax_address_requestResource {

    private final Logger log = LoggerFactory.getLogger(Tax_address_requestResource.class);

    @Inject
    private Tax_address_requestService tax_address_requestService;

    /**
     * POST  /tax-address-requests : Create a new tax_address_request.
     *
     * @param tax_address_request the tax_address_request to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_address_request, or with status 400 (Bad Request) if the tax_address_request has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-address-requests",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address_request> createTax_address_request(@Valid @RequestBody Tax_address_request tax_address_request) throws URISyntaxException {
        log.debug("REST request to save Tax_address_request : {}", tax_address_request);
        if (tax_address_request.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_address_request", "idexists", "A new tax_address_request cannot already have an ID")).body(null);
        }
        Tax_address_request result = tax_address_requestService.save(tax_address_request);
        return ResponseEntity.created(new URI("/api/tax-address-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_address_request", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-address-requests : Updates an existing tax_address_request.
     *
     * @param tax_address_request the tax_address_request to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_address_request,
     * or with status 400 (Bad Request) if the tax_address_request is not valid,
     * or with status 500 (Internal Server Error) if the tax_address_request couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-address-requests",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address_request> updateTax_address_request(@Valid @RequestBody Tax_address_request tax_address_request) throws URISyntaxException {
        log.debug("REST request to update Tax_address_request : {}", tax_address_request);
        if (tax_address_request.getId() == null) {
            return createTax_address_request(tax_address_request);
        }
        Tax_address_request result = tax_address_requestService.save(tax_address_request);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_address_request", tax_address_request.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-address-requests : get all the tax_address_requests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_address_requests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-address-requests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_address_request>> getAllTax_address_requests(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_address_requests");
        Page<Tax_address_request> page = tax_address_requestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-address-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-address-requests/:id : get the "id" tax_address_request.
     *
     * @param id the id of the tax_address_request to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_address_request, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-address-requests/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address_request> getTax_address_request(@PathVariable Long id) {
        log.debug("REST request to get Tax_address_request : {}", id);
        if(id == 0){
            Tax_address_request tax_address_request1 = new Tax_address_request();
            return new ResponseEntity<>( tax_address_request1,HttpStatus.OK);
        }
        Tax_address_request tax_address_request = tax_address_requestService.findOne(id);
        return Optional.ofNullable(tax_address_request)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-address-requests/:id : delete the "id" tax_address_request.
     *
     * @param id the id of the tax_address_request to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-address-requests/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_address_request(@PathVariable Long id) {
        log.debug("REST request to delete Tax_address_request : {}", id);
        tax_address_requestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_address_request", id.toString())).build();
    }

}
