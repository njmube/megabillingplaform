package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Price_concept;
import org.megapractical.billingplatform.service.Price_conceptService;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Price_concept.
 */
@RestController
@RequestMapping("/api")
public class Price_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Price_conceptResource.class);

    @Inject
    private Price_conceptService price_conceptService;

    /**
     * POST  /price-concepts : Create a new price_concept.
     *
     * @param price_concept the price_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new price_concept, or with status 400 (Bad Request) if the price_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/price-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price_concept> createPrice_concept(@Valid @RequestBody Price_concept price_concept) throws URISyntaxException {
        log.debug("REST request to save Price_concept : {}", price_concept);
        if (price_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("price_concept", "idexists", "A new price_concept cannot already have an ID")).body(null);
        }
        Price_concept result = price_conceptService.save(price_concept);
        return ResponseEntity.created(new URI("/api/price-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("price_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-concepts : Updates an existing price_concept.
     *
     * @param price_concept the price_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated price_concept,
     * or with status 400 (Bad Request) if the price_concept is not valid,
     * or with status 500 (Internal Server Error) if the price_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/price-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price_concept> updatePrice_concept(@Valid @RequestBody Price_concept price_concept) throws URISyntaxException {
        log.debug("REST request to update Price_concept : {}", price_concept);
        if (price_concept.getId() == null) {
            return createPrice_concept(price_concept);
        }
        Price_concept result = price_conceptService.save(price_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("price_concept", price_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-concepts : get all the price_concepts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of price_concepts in body
     */
    @RequestMapping(value = "/price-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayerconcept"})
    @Timed
    public List<Price_concept> getAllPrice_concepts(@RequestParam(value = "taxpayerconcept") Long taxpayerconcept) {
        log.debug("REST request to get all Price_concepts");
        return price_conceptService.findAll(taxpayerconcept);
    }

    /**
     * GET  /price-concepts/:id : get the "id" price_concept.
     *
     * @param id the id of the price_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the price_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/price-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Price_concept> getPrice_concept(@PathVariable Long id) {
        log.debug("REST request to get Price_concept : {}", id);
        Price_concept price_concept = price_conceptService.findOne(id);
        return Optional.ofNullable(price_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /price-concepts/:id : delete the "id" price_concept.
     *
     * @param id the id of the price_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/price-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrice_concept(@PathVariable Long id) {
        log.debug("REST request to delete Price_concept : {}", id);
        price_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("price_concept", id.toString())).build();
    }

}
