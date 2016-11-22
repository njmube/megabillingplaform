package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Com_tariff_fraction;
import org.megapractical.billingplatform.service.Com_tariff_fractionService;
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
 * REST controller for managing Com_tariff_fraction.
 */
@RestController
@RequestMapping("/api")
public class Com_tariff_fractionResource {

    private final Logger log = LoggerFactory.getLogger(Com_tariff_fractionResource.class);

    @Inject
    private Com_tariff_fractionService com_tariff_fractionService;

    /**
     * POST  /com-tariff-fractions : Create a new com_tariff_fraction.
     *
     * @param com_tariff_fraction the com_tariff_fraction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new com_tariff_fraction, or with status 400 (Bad Request) if the com_tariff_fraction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-tariff-fractions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tariff_fraction> createCom_tariff_fraction(@Valid @RequestBody Com_tariff_fraction com_tariff_fraction) throws URISyntaxException {
        log.debug("REST request to save Com_tariff_fraction : {}", com_tariff_fraction);
        if (com_tariff_fraction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("com_tariff_fraction", "idexists", "A new com_tariff_fraction cannot already have an ID")).body(null);
        }
        Com_tariff_fraction result = com_tariff_fractionService.save(com_tariff_fraction);
        return ResponseEntity.created(new URI("/api/com-tariff-fractions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("com_tariff_fraction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /com-tariff-fractions : Updates an existing com_tariff_fraction.
     *
     * @param com_tariff_fraction the com_tariff_fraction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated com_tariff_fraction,
     * or with status 400 (Bad Request) if the com_tariff_fraction is not valid,
     * or with status 500 (Internal Server Error) if the com_tariff_fraction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/com-tariff-fractions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tariff_fraction> updateCom_tariff_fraction(@Valid @RequestBody Com_tariff_fraction com_tariff_fraction) throws URISyntaxException {
        log.debug("REST request to update Com_tariff_fraction : {}", com_tariff_fraction);
        if (com_tariff_fraction.getId() == null) {
            return createCom_tariff_fraction(com_tariff_fraction);
        }
        Com_tariff_fraction result = com_tariff_fractionService.save(com_tariff_fraction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("com_tariff_fraction", com_tariff_fraction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /com-tariff-fractions : get all the com_tariff_fractions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of com_tariff_fractions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/com-tariff-fractions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"pg"})
    @Timed
    public ResponseEntity<List<Com_tariff_fraction>> getAllCom_tariff_fractions(Pageable pageable, @RequestParam(value = "pg") Integer pg)
        throws URISyntaxException {
        log.debug("REST request to get a page of Com_tariff_fractions");
        if(pg == 0) {
            Page<Com_tariff_fraction> page = com_tariff_fractionService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/com-tariff-fractions");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
        else{
            List<Com_tariff_fraction> page = com_tariff_fractionService.findAll();
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
    }

    /**
     * GET  /com-tariff-fractions/:id : get the "id" com_tariff_fraction.
     *
     * @param id the id of the com_tariff_fraction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the com_tariff_fraction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/com-tariff-fractions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Com_tariff_fraction> getCom_tariff_fraction(@PathVariable Long id) {
        log.debug("REST request to get Com_tariff_fraction : {}", id);
        Com_tariff_fraction com_tariff_fraction = com_tariff_fractionService.findOne(id);
        return Optional.ofNullable(com_tariff_fraction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /com-tariff-fractions/:id : delete the "id" com_tariff_fraction.
     *
     * @param id the id of the com_tariff_fraction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/com-tariff-fractions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCom_tariff_fraction(@PathVariable Long id) {
        log.debug("REST request to delete Com_tariff_fraction : {}", id);
        com_tariff_fractionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("com_tariff_fraction", id.toString())).build();
    }

}
