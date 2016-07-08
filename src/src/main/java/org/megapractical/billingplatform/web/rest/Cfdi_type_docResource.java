package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Cfdi_type_doc;
import org.megapractical.billingplatform.service.Cfdi_type_docService;
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
 * REST controller for managing Cfdi_type_doc.
 */
@RestController
@RequestMapping("/api")
public class Cfdi_type_docResource {

    private final Logger log = LoggerFactory.getLogger(Cfdi_type_docResource.class);

    @Inject
    private Cfdi_type_docService cfdi_type_docService;

    /**
     * POST  /cfdi-type-docs : Create a new cfdi_type_doc.
     *
     * @param cfdi_type_doc the cfdi_type_doc to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi_type_doc, or with status 400 (Bad Request) if the cfdi_type_doc has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-type-docs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_type_doc> createCfdi_type_doc(@Valid @RequestBody Cfdi_type_doc cfdi_type_doc) throws URISyntaxException {
        log.debug("REST request to save Cfdi_type_doc : {}", cfdi_type_doc);
        if (cfdi_type_doc.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi_type_doc", "idexists", "A new cfdi_type_doc cannot already have an ID")).body(null);
        }
        Cfdi_type_doc result = cfdi_type_docService.save(cfdi_type_doc);
        return ResponseEntity.created(new URI("/api/cfdi-type-docs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi_type_doc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdi-type-docs : Updates an existing cfdi_type_doc.
     *
     * @param cfdi_type_doc the cfdi_type_doc to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi_type_doc,
     * or with status 400 (Bad Request) if the cfdi_type_doc is not valid,
     * or with status 500 (Internal Server Error) if the cfdi_type_doc couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-type-docs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_type_doc> updateCfdi_type_doc(@Valid @RequestBody Cfdi_type_doc cfdi_type_doc) throws URISyntaxException {
        log.debug("REST request to update Cfdi_type_doc : {}", cfdi_type_doc);
        if (cfdi_type_doc.getId() == null) {
            return createCfdi_type_doc(cfdi_type_doc);
        }
        Cfdi_type_doc result = cfdi_type_docService.save(cfdi_type_doc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi_type_doc", cfdi_type_doc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdi-type-docs : get all the cfdi_type_docs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdi_type_docs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdi-type-docs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"filtername"})
    @Timed
    public ResponseEntity<List<Cfdi_type_doc>> getAllCfdi_type_docs(@RequestParam(value = "filtername") String filtername,
                                                                    Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdi_type_docs");
        if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
            Page<Cfdi_type_doc> page = cfdi_type_docService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdi-type-docs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }else {
            Page<Cfdi_type_doc> page = cfdi_type_docService.findAllByName(filtername,pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdi-type-docs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        }
    }

    /**
     * GET  /cfdi-type-docs/:id : get the "id" cfdi_type_doc.
     *
     * @param id the id of the cfdi_type_doc to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi_type_doc, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdi-type-docs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_type_doc> getCfdi_type_doc(@PathVariable Long id) {
        log.debug("REST request to get Cfdi_type_doc : {}", id);
        Cfdi_type_doc cfdi_type_doc = cfdi_type_docService.findOne(id);
        return Optional.ofNullable(cfdi_type_doc)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdi-type-docs/:id : delete the "id" cfdi_type_doc.
     *
     * @param id the id of the cfdi_type_doc to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdi-type-docs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi_type_doc(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi_type_doc : {}", id);
        cfdi_type_docService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi_type_doc", id.toString())).build();
    }

}
