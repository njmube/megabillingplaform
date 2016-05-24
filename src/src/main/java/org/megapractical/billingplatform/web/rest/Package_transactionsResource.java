package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Package_transactions;
import org.megapractical.billingplatform.service.Package_transactionsService;
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
 * REST controller for managing Package_transactions.
 */
@RestController
@RequestMapping("/api")
public class Package_transactionsResource {

    private final Logger log = LoggerFactory.getLogger(Package_transactionsResource.class);
        
    @Inject
    private Package_transactionsService package_transactionsService;
    
    /**
     * POST  /package-transactions : Create a new package_transactions.
     *
     * @param package_transactions the package_transactions to create
     * @return the ResponseEntity with status 201 (Created) and with body the new package_transactions, or with status 400 (Bad Request) if the package_transactions has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-transactions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_transactions> createPackage_transactions(@RequestBody Package_transactions package_transactions) throws URISyntaxException {
        log.debug("REST request to save Package_transactions : {}", package_transactions);
        if (package_transactions.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("package_transactions", "idexists", "A new package_transactions cannot already have an ID")).body(null);
        }
        Package_transactions result = package_transactionsService.save(package_transactions);
        return ResponseEntity.created(new URI("/api/package-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("package_transactions", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /package-transactions : Updates an existing package_transactions.
     *
     * @param package_transactions the package_transactions to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated package_transactions,
     * or with status 400 (Bad Request) if the package_transactions is not valid,
     * or with status 500 (Internal Server Error) if the package_transactions couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/package-transactions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_transactions> updatePackage_transactions(@RequestBody Package_transactions package_transactions) throws URISyntaxException {
        log.debug("REST request to update Package_transactions : {}", package_transactions);
        if (package_transactions.getId() == null) {
            return createPackage_transactions(package_transactions);
        }
        Package_transactions result = package_transactionsService.save(package_transactions);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("package_transactions", package_transactions.getId().toString()))
            .body(result);
    }

    /**
     * GET  /package-transactions : get all the package_transactions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of package_transactions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/package-transactions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Package_transactions>> getAllPackage_transactions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Package_transactions");
        Page<Package_transactions> page = package_transactionsService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/package-transactions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /package-transactions/:id : get the "id" package_transactions.
     *
     * @param id the id of the package_transactions to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the package_transactions, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/package-transactions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Package_transactions> getPackage_transactions(@PathVariable Long id) {
        log.debug("REST request to get Package_transactions : {}", id);
        Package_transactions package_transactions = package_transactionsService.findOne(id);
        return Optional.ofNullable(package_transactions)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /package-transactions/:id : delete the "id" package_transactions.
     *
     * @param id the id of the package_transactions to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/package-transactions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePackage_transactions(@PathVariable Long id) {
        log.debug("REST request to delete Package_transactions : {}", id);
        package_transactionsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("package_transactions", id.toString())).build();
    }

}
