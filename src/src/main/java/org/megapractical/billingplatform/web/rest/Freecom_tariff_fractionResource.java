package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Freecom_tariff_fraction;
import org.megapractical.billingplatform.service.Freecom_tariff_fractionService;
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
 * REST controller for managing Freecom_tariff_fraction.
 */
@RestController
@RequestMapping("/api")
public class Freecom_tariff_fractionResource {

    private final Logger log = LoggerFactory.getLogger(Freecom_tariff_fractionResource.class);

    @Inject
    private Freecom_tariff_fractionService freecom_tariff_fractionService;

    /**
     * POST  /freecom-tariff-fractions : Create a new freecom_tariff_fraction.
     *
     * @param freecom_tariff_fraction the freecom_tariff_fraction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new freecom_tariff_fraction, or with status 400 (Bad Request) if the freecom_tariff_fraction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tariff-fractions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tariff_fraction> createFreecom_tariff_fraction(@Valid @RequestBody Freecom_tariff_fraction freecom_tariff_fraction) throws URISyntaxException {
        log.debug("REST request to save Freecom_tariff_fraction : {}", freecom_tariff_fraction);
        if (freecom_tariff_fraction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("freecom_tariff_fraction", "idexists", "A new freecom_tariff_fraction cannot already have an ID")).body(null);
        }
        Freecom_tariff_fraction result = freecom_tariff_fractionService.save(freecom_tariff_fraction);
        return ResponseEntity.created(new URI("/api/freecom-tariff-fractions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("freecom_tariff_fraction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /freecom-tariff-fractions : Updates an existing freecom_tariff_fraction.
     *
     * @param freecom_tariff_fraction the freecom_tariff_fraction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated freecom_tariff_fraction,
     * or with status 400 (Bad Request) if the freecom_tariff_fraction is not valid,
     * or with status 500 (Internal Server Error) if the freecom_tariff_fraction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/freecom-tariff-fractions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tariff_fraction> updateFreecom_tariff_fraction(@Valid @RequestBody Freecom_tariff_fraction freecom_tariff_fraction) throws URISyntaxException {
        log.debug("REST request to update Freecom_tariff_fraction : {}", freecom_tariff_fraction);
        if (freecom_tariff_fraction.getId() == null) {
            return createFreecom_tariff_fraction(freecom_tariff_fraction);
        }
        Freecom_tariff_fraction result = freecom_tariff_fractionService.save(freecom_tariff_fraction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("freecom_tariff_fraction", freecom_tariff_fraction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /freecom-tariff-fractions : get all the freecom_tariff_fractions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of freecom_tariff_fractions in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/freecom-tariff-fractions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"pg"})
    @Timed
    public ResponseEntity<List<Freecom_tariff_fraction>> getAllFreecom_tariff_fractions(Pageable pageable, @RequestParam(value = "pg") Integer pg)
        throws URISyntaxException {
        log.debug("REST request to get a page of Freecom_tariff_fractions");
        if (pg == 0) {
            Page<Freecom_tariff_fraction> page = freecom_tariff_fractionService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/freecom-tariff-fractions");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
        else{
            List<Freecom_tariff_fraction> page = freecom_tariff_fractionService.findAll();
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
    }

    /**
     * GET  /freecom-tariff-fractions/:id : get the "id" freecom_tariff_fraction.
     *
     * @param id the id of the freecom_tariff_fraction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the freecom_tariff_fraction, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/freecom-tariff-fractions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Freecom_tariff_fraction> getFreecom_tariff_fraction(@PathVariable Long id) {
        log.debug("REST request to get Freecom_tariff_fraction : {}", id);
        Freecom_tariff_fraction freecom_tariff_fraction = freecom_tariff_fractionService.findOne(id);
        return Optional.ofNullable(freecom_tariff_fraction)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /freecom-tariff-fractions/:id : delete the "id" freecom_tariff_fraction.
     *
     * @param id the id of the freecom_tariff_fraction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/freecom-tariff-fractions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFreecom_tariff_fraction(@PathVariable Long id) {
        log.debug("REST request to delete Freecom_tariff_fraction : {}", id);
        freecom_tariff_fractionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("freecom_tariff_fraction", id.toString())).build();
    }

}
