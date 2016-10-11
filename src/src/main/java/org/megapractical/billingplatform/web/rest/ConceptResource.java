package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Concept;
import org.megapractical.billingplatform.domain.Customs_info;
import org.megapractical.billingplatform.domain.Part_concept;
import org.megapractical.billingplatform.domain.Tax_concept;
import org.megapractical.billingplatform.service.ConceptService;
import org.megapractical.billingplatform.service.Customs_infoService;
import org.megapractical.billingplatform.service.Part_conceptService;
import org.megapractical.billingplatform.service.Tax_conceptService;
import org.megapractical.billingplatform.web.rest.dto.ConceptDTO;
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
 * REST controller for managing Concept.
 */
@RestController
@RequestMapping("/api")
public class ConceptResource {

    private final Logger log = LoggerFactory.getLogger(ConceptResource.class);

    @Inject
    private ConceptService conceptService;

    @Inject
    private Part_conceptService part_conceptService;

    @Inject
    private Customs_infoService customs_infoService;

    @Inject
    private Tax_conceptService tax_conceptService;

    /**
     * POST  /concepts : Create a new concept.
     *
     * @param conceptDTO the concept to create
     * @return the ResponseEntity with status 201 (Created) and with body the new concept, or with status 400 (Bad Request) if the concept has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> createConcept(@Valid @RequestBody ConceptDTO conceptDTO) throws URISyntaxException {
        Concept concept = conceptDTO.getConcept();
        log.debug("REST request to save Concept : {}", concept);
        if (concept.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("concept", "idexists", "A new concept cannot already have an ID")).body(null);
        }

        Concept result = conceptService.save(concept);

        savePartsAndCustomInfos(conceptDTO, result);

        return ResponseEntity.created(new URI("/api/concepts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("concept", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /concepts : Updates an existing concept.
     *
     * @param conceptDTO the concept to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated concept,
     * or with status 400 (Bad Request) if the concept is not valid,
     * or with status 500 (Internal Server Error) if the concept couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> updateConcept(@Valid @RequestBody ConceptDTO conceptDTO) throws URISyntaxException {
        Concept concept = conceptDTO.getConcept();
        log.debug("REST request to update Concept : {}", concept);
        if (concept.getId() == null) {
            return createConcept(conceptDTO);
        }
        Concept result = conceptService.save(concept);

        savePartsAndCustomInfos(conceptDTO, result);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("concept", concept.getId().toString()))
            .body(result);
    }

    private void savePartsAndCustomInfos(ConceptDTO conceptDTO, Concept concept){
        //Saving part concepts
        List<Part_concept> part_concepts = conceptDTO.getPart_concepts();
        for(Part_concept part_concept: part_concepts){
            part_concept.setConcept(concept);
            part_conceptService.save(part_concept);
        }

        //Sanving custom info
        List<Customs_info> customs_infos = conceptDTO.getCustoms_infos();
        for(Customs_info customs_info: customs_infos){
            customs_info.setConcept(concept);
            customs_infoService.save(customs_info);
        }
    }

    /**
     * GET  /concepts : get all the concepts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of concepts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/concepts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount", "no_identification", "description", "measure_unit", "unit_value"})
    @Timed
    public ResponseEntity<List<Concept>> getAllConcepts(Pageable pageable,
                                                        @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount,
                                                        @RequestParam(value = "no_identification") String no_identification,
                                                        @RequestParam(value = "description") String description,
                                                        @RequestParam(value = "measure_unit") String measure_unit,
                                                        @RequestParam(value = "unit_value") BigDecimal unit_value)
        throws URISyntaxException {
        log.debug("REST request to get a page of Concepts");
        Page<Concept> page = conceptService.findAll(pageable, taxpayeraccount, no_identification, description, measure_unit, unit_value);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/concepts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /concepts/:id : get the "id" concept.
     *
     * @param id the id of the concept to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the concept, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/concepts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Concept> getConcept(@PathVariable Long id) {
        log.debug("REST request to get Concept : {}", id);
        Concept concept = conceptService.findOne(id);
        return Optional.ofNullable(concept)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /concepts/:id : delete the "id" concept.
     *
     * @param id the id of the concept to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/concepts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount"})
    @Timed
    public ResponseEntity<Void> deleteConcept(@PathVariable Long id, @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount) {
        log.debug("REST request to delete Concept : {}", id);

        Sort defaultSort = new Sort(new Sort.Order(Sort.Direction.ASC, "id"));
        Pageable pageable = new PageRequest(0, 30, defaultSort);
        Integer concept_id = new Integer(id.toString());

        //Get and Delete all part_concepts
        Page<Part_concept> part_concept_page = part_conceptService.findAll(pageable, concept_id);
        for(Part_concept part_concept: part_concept_page.getContent()){
            part_conceptService.delete(part_concept.getId());
        }

        //Get and Delete all customs_infos
        Page<Customs_info> customs_info_page = customs_infoService.findAll(pageable, concept_id);
        for(Customs_info customs_info: customs_info_page.getContent()){
            customs_infoService.delete(customs_info.getId());
        }

        //Get and Delete all tax_concepts
        BigDecimal rate = new BigDecimal("-1");
        Page<Tax_concept> tax_concept_page = tax_conceptService.findAll(pageable, taxpayeraccount, " ", rate, " ");
        for(Tax_concept tax_concept: tax_concept_page.getContent()){
            if(tax_concept.getConcept().getId().compareTo(id) == 0) {
                tax_conceptService.delete(tax_concept.getId());
            }
        }

        conceptService.delete(id);

        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("concept", id.toString())).build();
    }

}
