package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Branch_office;
import org.megapractical.billingplatform.domain.Tax_address;
import org.megapractical.billingplatform.service.Branch_officeService;
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
 * REST controller for managing Branch_office.
 */
@RestController
@RequestMapping("/api")
public class Branch_officeResource {

    private final Logger log = LoggerFactory.getLogger(Branch_officeResource.class);

    @Inject
    private Branch_officeService branch_officeService;

    @Inject
    private Tax_addressService tax_addressService;

    /**
     * POST  /branch-offices : Create a new branch_office.
     *
     * @param branch_office the branch_office to create
     * @return the ResponseEntity with status 201 (Created) and with body the new branch_office, or with status 400 (Bad Request) if the branch_office has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/branch-offices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Branch_office> createBranch_office(@Valid @RequestBody Branch_office branch_office) throws URISyntaxException {
        log.debug("REST request to save Branch_office : {}", branch_office);
        if (branch_office.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("branch_office", "idexists", "A new branch_office cannot already have an ID")).body(null);
        }
        Tax_address tax_address = branch_office.getTax_address();
        branch_office.setTax_address(tax_addressService.save(tax_address));
        Branch_office result = branch_officeService.save(branch_office);
        return ResponseEntity.created(new URI("/api/branch-offices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("branch_office", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /branch-offices : Updates an existing branch_office.
     *
     * @param branch_office the branch_office to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated branch_office,
     * or with status 400 (Bad Request) if the branch_office is not valid,
     * or with status 500 (Internal Server Error) if the branch_office couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/branch-offices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Branch_office> updateBranch_office(@Valid @RequestBody Branch_office branch_office) throws URISyntaxException {
        log.debug("REST request to update Branch_office : {}", branch_office);
        if (branch_office.getId() == null) {
            return createBranch_office(branch_office);
        }
        tax_addressService.save(branch_office.getTax_address());
        Branch_office result = branch_officeService.save(branch_office);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("branch_office", branch_office.getId().toString()))
            .body(result);
    }

    /**
     * GET  /branch-offices : get all the branch_offices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of branch_offices in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/branch-offices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount"})
    @Timed
    public ResponseEntity<List<Branch_office>> getAllBranch_offices(Pageable pageable,
                                                                    @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount)
        throws URISyntaxException {
        log.debug("REST request to get a page of Branch_offices");
        Page<Branch_office> page = branch_officeService.findAll(pageable, taxpayeraccount);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/branch-offices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    /**
     * GET  /branch-offices/:id : get the "id" branch_office.
     *
     * @param id the id of the branch_office to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the branch_office, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/branch-offices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Branch_office> getBranch_office(@PathVariable Long id) {
        log.debug("REST request to get Branch_office : {}", id);
        Branch_office branch_office = branch_officeService.findOne(id);
        return Optional.ofNullable(branch_office)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /branch-offices/:id : delete the "id" branch_office.
     *
     * @param id the id of the branch_office to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/branch-offices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBranch_office(@PathVariable Long id) {
        log.debug("REST request to delete Branch_office : {}", id);
        branch_officeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("branch_office", id.toString())).build();
    }

}
