package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Discount_concept;
import org.megapractical.billingplatform.service.Discount_conceptService;
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
 * REST controller for managing Discount_concept.
 */
@RestController
@RequestMapping("/api")
public class Discount_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Discount_conceptResource.class);

    @Inject
    private Discount_conceptService discount_conceptService;

    /**
     * POST  /discount-concepts : Create a new discount_concept.
     *
     * @param discount_concept the discount_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discount_concept, or with status 400 (Bad Request) if the discount_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discount-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount_concept> createDiscount_concept(@Valid @RequestBody Discount_concept discount_concept) throws URISyntaxException {
        log.debug("REST request to save Discount_concept : {}", discount_concept);
        if (discount_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("discount_concept", "idexists", "A new discount_concept cannot already have an ID")).body(null);
        }
        Discount_concept result = discount_conceptService.save(discount_concept);
        return ResponseEntity.created(new URI("/api/discount-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("discount_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discount-concepts : Updates an existing discount_concept.
     *
     * @param discount_concept the discount_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discount_concept,
     * or with status 400 (Bad Request) if the discount_concept is not valid,
     * or with status 500 (Internal Server Error) if the discount_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/discount-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount_concept> updateDiscount_concept(@Valid @RequestBody Discount_concept discount_concept) throws URISyntaxException {
        log.debug("REST request to update Discount_concept : {}", discount_concept);
        if (discount_concept.getId() == null) {
            return createDiscount_concept(discount_concept);
        }
        Discount_concept result = discount_conceptService.save(discount_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("discount_concept", discount_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discount-concepts : get all the discount_concepts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of discount_concepts in body
     */
    @RequestMapping(value = "/discount-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayerconcept"})
    @Timed
    public List<Discount_concept> getAllDiscount_concepts(@RequestParam(value = "taxpayerconcept") Long taxpayerconcept) {
        log.debug("REST request to get all Discount_concepts");
        return discount_conceptService.findAll(taxpayerconcept);
    }

    /**
     * GET  /discount-concepts/:id : get the "id" discount_concept.
     *
     * @param id the id of the discount_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discount_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/discount-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Discount_concept> getDiscount_concept(@PathVariable Long id) {
        log.debug("REST request to get Discount_concept : {}", id);
        Discount_concept discount_concept = discount_conceptService.findOne(id);
        return Optional.ofNullable(discount_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /discount-concepts/:id : delete the "id" discount_concept.
     *
     * @param id the id of the discount_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/discount-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDiscount_concept(@PathVariable Long id) {
        log.debug("REST request to delete Discount_concept : {}", id);
        discount_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("discount_concept", id.toString())).build();
    }

}
