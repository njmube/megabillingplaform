package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Cfdi;
import org.megapractical.billingplatform.service.CfdiService;
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
 * REST controller for managing Cfdi.
 */
@RestController
@RequestMapping("/api")
public class CfdiResource {

    private final Logger log = LoggerFactory.getLogger(CfdiResource.class);
        
    @Inject
    private CfdiService cfdiService;
    
    /**
     * POST  /cfdis : Create a new cfdi.
     *
     * @param cfdi the cfdi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi, or with status 400 (Bad Request) if the cfdi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> createCfdi(@Valid @RequestBody Cfdi cfdi) throws URISyntaxException {
        log.debug("REST request to save Cfdi : {}", cfdi);
        if (cfdi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi", "idexists", "A new cfdi cannot already have an ID")).body(null);
        }
        Cfdi result = cfdiService.save(cfdi);
        return ResponseEntity.created(new URI("/api/cfdis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdis : Updates an existing cfdi.
     *
     * @param cfdi the cfdi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi,
     * or with status 400 (Bad Request) if the cfdi is not valid,
     * or with status 500 (Internal Server Error) if the cfdi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> updateCfdi(@Valid @RequestBody Cfdi cfdi) throws URISyntaxException {
        log.debug("REST request to update Cfdi : {}", cfdi);
        if (cfdi.getId() == null) {
            return createCfdi(cfdi);
        }
        Cfdi result = cfdiService.save(cfdi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi", cfdi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdis : get all the cfdis.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdis in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdis",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cfdi>> getAllCfdis(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdis");
        Page<Cfdi> page = cfdiService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cfdis/:id : get the "id" cfdi.
     *
     * @param id the id of the cfdi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdis/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi> getCfdi(@PathVariable Long id) {
        log.debug("REST request to get Cfdi : {}", id);
        Cfdi cfdi = cfdiService.findOne(id);
        return Optional.ofNullable(cfdi)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdis/:id : delete the "id" cfdi.
     *
     * @param id the id of the cfdi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdis/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi : {}", id);
        cfdiService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi", id.toString())).build();
    }

}
