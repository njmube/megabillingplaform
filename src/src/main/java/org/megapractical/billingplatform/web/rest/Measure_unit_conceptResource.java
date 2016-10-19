package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Measure_unit_concept;
import org.megapractical.billingplatform.service.Measure_unit_conceptService;
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
 * REST controller for managing Measure_unit_concept.
 */
@RestController
@RequestMapping("/api")
public class Measure_unit_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Measure_unit_conceptResource.class);

    @Inject
    private Measure_unit_conceptService measure_unit_conceptService;

    /**
     * POST  /measure-unit-concepts : Create a new measure_unit_concept.
     *
     * @param measure_unit_concept the measure_unit_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new measure_unit_concept, or with status 400 (Bad Request) if the measure_unit_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/measure-unit-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit_concept> createMeasure_unit_concept(@Valid @RequestBody Measure_unit_concept measure_unit_concept) throws URISyntaxException {
        log.debug("REST request to save Measure_unit_concept : {}", measure_unit_concept);
        if (measure_unit_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("measure_unit_concept", "idexists", "A new measure_unit_concept cannot already have an ID")).body(null);
        }
        Measure_unit_concept result = measure_unit_conceptService.save(measure_unit_concept);
        return ResponseEntity.created(new URI("/api/measure-unit-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("measure_unit_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /measure-unit-concepts : Updates an existing measure_unit_concept.
     *
     * @param measure_unit_concept the measure_unit_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated measure_unit_concept,
     * or with status 400 (Bad Request) if the measure_unit_concept is not valid,
     * or with status 500 (Internal Server Error) if the measure_unit_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/measure-unit-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit_concept> updateMeasure_unit_concept(@Valid @RequestBody Measure_unit_concept measure_unit_concept) throws URISyntaxException {
        log.debug("REST request to update Measure_unit_concept : {}", measure_unit_concept);
        if (measure_unit_concept.getId() == null) {
            return createMeasure_unit_concept(measure_unit_concept);
        }
        Measure_unit_concept result = measure_unit_conceptService.save(measure_unit_concept);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("measure_unit_concept", measure_unit_concept.getId().toString()))
            .body(result);
    }

    /**
     * GET  /measure-unit-concepts : get all the measure_unit_concepts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of measure_unit_concepts in body
     */
    @RequestMapping(value = "/measure-unit-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayerconcept"})
    @Timed
    public List<Measure_unit_concept> getAllMeasure_unit_concepts(@RequestParam(value = "taxpayerconcept") Long taxpayerconcept) {
        log.debug("REST request to get all Measure_unit_concepts");
        return measure_unit_conceptService.findAll(taxpayerconcept);
    }

    /**
     * GET  /measure-unit-concepts/:id : get the "id" measure_unit_concept.
     *
     * @param id the id of the measure_unit_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the measure_unit_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/measure-unit-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Measure_unit_concept> getMeasure_unit_concept(@PathVariable Long id) {
        log.debug("REST request to get Measure_unit_concept : {}", id);
        Measure_unit_concept measure_unit_concept = measure_unit_conceptService.findOne(id);
        return Optional.ofNullable(measure_unit_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /measure-unit-concepts/:id : delete the "id" measure_unit_concept.
     *
     * @param id the id of the measure_unit_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/measure-unit-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMeasure_unit_concept(@PathVariable Long id) {
        log.debug("REST request to delete Measure_unit_concept : {}", id);
        measure_unit_conceptService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("measure_unit_concept", id.toString())).build();
    }

}
