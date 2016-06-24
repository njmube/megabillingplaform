package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_money;
import org.megapractical.billingplatform.service.C_moneyService;
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
 * REST controller for managing C_money.
 */
@RestController
@RequestMapping("/api")
public class C_moneyResource {

    private final Logger log = LoggerFactory.getLogger(C_moneyResource.class);
        
    @Inject
    private C_moneyService c_moneyService;
    
    /**
     * POST  /c-monies : Create a new c_money.
     *
     * @param c_money the c_money to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_money, or with status 400 (Bad Request) if the c_money has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-monies",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_money> createC_money(@Valid @RequestBody C_money c_money) throws URISyntaxException {
        log.debug("REST request to save C_money : {}", c_money);
        if (c_money.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_money", "idexists", "A new c_money cannot already have an ID")).body(null);
        }
        C_money result = c_moneyService.save(c_money);
        return ResponseEntity.created(new URI("/api/c-monies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_money", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-monies : Updates an existing c_money.
     *
     * @param c_money the c_money to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_money,
     * or with status 400 (Bad Request) if the c_money is not valid,
     * or with status 500 (Internal Server Error) if the c_money couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-monies",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_money> updateC_money(@Valid @RequestBody C_money c_money) throws URISyntaxException {
        log.debug("REST request to update C_money : {}", c_money);
        if (c_money.getId() == null) {
            return createC_money(c_money);
        }
        C_money result = c_moneyService.save(c_money);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_money", c_money.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-monies : get all the c_monies.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_monies in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-monies",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_money>> getAllC_monies(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_monies");
        Page<C_money> page = c_moneyService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-monies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-monies/:id : get the "id" c_money.
     *
     * @param id the id of the c_money to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_money, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-monies/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_money> getC_money(@PathVariable Long id) {
        log.debug("REST request to get C_money : {}", id);
        C_money c_money = c_moneyService.findOne(id);
        return Optional.ofNullable(c_money)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-monies/:id : delete the "id" c_money.
     *
     * @param id the id of the c_money to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-monies/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_money(@PathVariable Long id) {
        log.debug("REST request to delete C_money : {}", id);
        c_moneyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_money", id.toString())).build();
    }

}
