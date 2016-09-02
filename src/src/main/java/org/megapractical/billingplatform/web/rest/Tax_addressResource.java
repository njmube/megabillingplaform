package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Tax_address;
import org.megapractical.billingplatform.service.Tax_addressService;
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
 * REST controller for managing Tax_address.
 */
@RestController
@RequestMapping("/api")
public class Tax_addressResource {

    private final Logger log = LoggerFactory.getLogger(Tax_addressResource.class);
        
    @Inject
    private Tax_addressService tax_addressService;
    
    /**
     * POST  /tax-addresses : Create a new tax_address.
     *
     * @param tax_address the tax_address to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tax_address, or with status 400 (Bad Request) if the tax_address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-addresses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address> createTax_address(@Valid @RequestBody Tax_address tax_address) throws URISyntaxException {
        log.debug("REST request to save Tax_address : {}", tax_address);
        if (tax_address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tax_address", "idexists", "A new tax_address cannot already have an ID")).body(null);
        }
        Tax_address result = tax_addressService.save(tax_address);
        return ResponseEntity.created(new URI("/api/tax-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tax_address", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tax-addresses : Updates an existing tax_address.
     *
     * @param tax_address the tax_address to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tax_address,
     * or with status 400 (Bad Request) if the tax_address is not valid,
     * or with status 500 (Internal Server Error) if the tax_address couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/tax-addresses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address> updateTax_address(@Valid @RequestBody Tax_address tax_address) throws URISyntaxException {
        log.debug("REST request to update Tax_address : {}", tax_address);
        if (tax_address.getId() == null) {
            return createTax_address(tax_address);
        }
        Tax_address result = tax_addressService.save(tax_address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tax_address", tax_address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tax-addresses : get all the tax_addresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tax_addresses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tax-addresses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Tax_address>> getAllTax_addresses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tax_addresses");
        Page<Tax_address> page = tax_addressService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tax-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tax-addresses/:id : get the "id" tax_address.
     *
     * @param id the id of the tax_address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tax_address, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/tax-addresses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Tax_address> getTax_address(@PathVariable Long id) {
        log.debug("REST request to get Tax_address : {}", id);
        Tax_address tax_address = tax_addressService.findOne(id);
        return Optional.ofNullable(tax_address)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tax-addresses/:id : delete the "id" tax_address.
     *
     * @param id the id of the tax_address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/tax-addresses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTax_address(@PathVariable Long id) {
        log.debug("REST request to delete Tax_address : {}", id);
        tax_addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tax_address", id.toString())).build();
    }

}
