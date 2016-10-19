package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.*;
import org.megapractical.billingplatform.service.*;
import org.megapractical.billingplatform.web.rest.dto.TaxpayerConceptDTO;
import org.megapractical.billingplatform.web.rest.util.HeaderUtil;
import org.megapractical.billingplatform.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Taxpayer_concept.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_conceptResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_conceptResource.class);

    @Inject
    private Taxpayer_conceptService taxpayer_conceptService;

    @Inject
    private Measure_unit_conceptService measure_unit_conceptService;

    @Inject
    private Price_conceptService price_conceptService;

    @Inject
    private Discount_conceptService discount_conceptService;

    @Inject
    private Tax_conceptService tax_conceptService;

    /**
     * POST  /taxpayer-concepts : Create a new taxpayer_concept.
     *
     * @param taxpayer_conceptDTO the taxpayer_concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_concept, or with status 400 (Bad Request) if the taxpayer_concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_concept> createTaxpayer_concept(@RequestBody TaxpayerConceptDTO taxpayer_conceptDTO) throws URISyntaxException {
        Taxpayer_concept taxpayer_concept = taxpayer_conceptDTO.getTaxpayer_concept();
        log.debug("REST request to save Taxpayer_concept : {}", taxpayer_concept);
        if (taxpayer_concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_concept", "idexists", "A new taxpayer_concept cannot already have an ID")).body(null);
        }

        Taxpayer_concept result = taxpayer_conceptService.save(taxpayer_concept);

        taxpayer_conceptDTO.setTaxpayer_concept(result);
        saveMeasueUnitsPricesAndDiscounts(taxpayer_conceptDTO);

        return ResponseEntity.created(new URI("/api/taxpayer-concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-concepts : Updates an existing taxpayer_concept.
     *
     * @param taxpayer_conceptDTO the taxpayer_concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_concept,
     * or with status 400 (Bad Request) if the taxpayer_concept is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_concept> updateTaxpayer_concept(@RequestBody TaxpayerConceptDTO taxpayer_conceptDTO) throws URISyntaxException {
        Taxpayer_concept taxpayer_concept = taxpayer_conceptDTO.getTaxpayer_concept();
        log.debug("REST request to update Taxpayer_concept : {}", taxpayer_concept);
        if (taxpayer_concept.getId() == null) {
            return createTaxpayer_concept(taxpayer_conceptDTO);
        }

        Taxpayer_concept result = taxpayer_conceptService.save(taxpayer_concept);

        taxpayer_conceptDTO.setTaxpayer_concept(result);
        saveMeasueUnitsPricesAndDiscounts(taxpayer_conceptDTO);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_concept", taxpayer_concept.getId().toString()))
            .body(result);
    }

    private void saveMeasueUnitsPricesAndDiscounts(TaxpayerConceptDTO taxpayer_conceptDTO) {
        Taxpayer_concept taxpayer_concept = taxpayer_conceptDTO.getTaxpayer_concept();

        //Saving measure_units
        List<Measure_unit_concept> measure_unit_concepts = taxpayer_conceptDTO.getMeasure_units();
        for (Measure_unit_concept measure_unit_concept : measure_unit_concepts) {
            measure_unit_concept.setTaxpayer_concept(taxpayer_concept);
            measure_unit_conceptService.save(measure_unit_concept);
        }

        //Saving prices
        List<Price_concept> price_concepts = taxpayer_conceptDTO.getPrices();
        for (Price_concept price_concept : price_concepts) {
            price_concept.setTaxpayer_concept(taxpayer_concept);
            price_conceptService.save(price_concept);
        }

        //Saving discounts
        List<Discount_concept> discount_concepts = taxpayer_conceptDTO.getDiscounts();
        for (Discount_concept discount_concept : discount_concepts) {
            discount_concept.setTaxpayer_concept(taxpayer_concept);
            discount_conceptService.save(discount_concept);
        }

    }

    /**
     * GET  /taxpayer-concepts : get all the taxpayer_concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount", "no_identification", "description", "measure_unit", "unit_value"})
    @Timed
    public ResponseEntity<List<Taxpayer_concept>> getAllTaxpayer_concepts(Pageable pageable,
                                                                          @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount,
                                                                          @RequestParam(value = "no_identification") String no_identification,
                                                                          @RequestParam(value = "description") String description,
                                                                          @RequestParam(value = "measure_unit") String measure_unit,
                                                                          @RequestParam(value = "unit_value") BigDecimal unit_value)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_concepts");
        Page<Taxpayer_concept> page = taxpayer_conceptService.findAll(pageable, taxpayeraccount, no_identification, description, measure_unit, unit_value);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-concepts/:id : get the "id" taxpayer_concept.
     *
     * @param id the id of the taxpayer_concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_concept> getTaxpayer_concept(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_concept : {}", id);
        Taxpayer_concept taxpayer_concept = taxpayer_conceptService.findOne(id);
        return Optional.ofNullable(taxpayer_concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-concepts/:id : delete the "id" taxpayer_concept.
     *
     * @param id the id of the taxpayer_concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount"})
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_concept(@PathVariable Long id, @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount) {
        log.debug("REST request to delete Taxpayer_concept : {}", id);

        //Get and deleting all measure_units
        List<Measure_unit_concept> measure_unit_concepts = measure_unit_conceptService.findAll(id);
        for (Measure_unit_concept measure_unit_concept: measure_unit_concepts){
            measure_unit_conceptService.delete(measure_unit_concept.getId());
        }

        //Get and deleting all prices
        List<Price_concept> price_concepts = price_conceptService.findAll(id);
        for (Price_concept price_concept: price_concepts){
            price_conceptService.delete(price_concept.getId());
        }

        //Get and deleting all discounts
        List<Discount_concept> discount_concepts = discount_conceptService.findAll(id);
        for (Discount_concept discount_concept: discount_concepts){
            discount_conceptService.delete(discount_concept.getId());
        }

        //Get and deleting all tax concepts
        Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(0, 30, defaultSort);
        BigDecimal rate = new BigDecimal("-1");

        Page<Tax_concept> tax_concept_page = tax_conceptService.findAll(pageable, taxpayeraccount, " ", rate, " ");
        for(Tax_concept tax_concept: tax_concept_page.getContent()){
            if(tax_concept.getTaxpayer_concept().getId().compareTo(id) == 0) {
                tax_conceptService.delete(tax_concept.getId());
            }
        }

        taxpayer_conceptService.delete(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_concept", id.toString())).build();
    }

}
