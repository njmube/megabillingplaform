package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Cfdi_template;
import org.megapractical.billingplatform.service.Cfdi_templateService;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Cfdi_template.
 */
@RestController
@RequestMapping("/api")
public class Cfdi_templateResource {

    private final Logger log = LoggerFactory.getLogger(Cfdi_templateResource.class);
        
    @Inject
    private Cfdi_templateService cfdi_templateService;
    
    /**
     * POST  /cfdi-templates : Create a new cfdi_template.
     *
     * @param cfdi_template the cfdi_template to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cfdi_template, or with status 400 (Bad Request) if the cfdi_template has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-templates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_template> createCfdi_template(@RequestBody Cfdi_template cfdi_template) throws URISyntaxException {
        log.debug("REST request to save Cfdi_template : {}", cfdi_template);
        if (cfdi_template.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("cfdi_template", "idexists", "A new cfdi_template cannot already have an ID")).body(null);
        }
        Cfdi_template result = cfdi_templateService.save(cfdi_template);
        return ResponseEntity.created(new URI("/api/cfdi-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cfdi_template", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cfdi-templates : Updates an existing cfdi_template.
     *
     * @param cfdi_template the cfdi_template to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cfdi_template,
     * or with status 400 (Bad Request) if the cfdi_template is not valid,
     * or with status 500 (Internal Server Error) if the cfdi_template couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cfdi-templates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_template> updateCfdi_template(@RequestBody Cfdi_template cfdi_template) throws URISyntaxException {
        log.debug("REST request to update Cfdi_template : {}", cfdi_template);
        if (cfdi_template.getId() == null) {
            return createCfdi_template(cfdi_template);
        }
        Cfdi_template result = cfdi_templateService.save(cfdi_template);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cfdi_template", cfdi_template.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cfdi-templates : get all the cfdi_templates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cfdi_templates in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cfdi-templates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Cfdi_template>> getAllCfdi_templates(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cfdi_templates");
        Page<Cfdi_template> page = cfdi_templateService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cfdi-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cfdi-templates/:id : get the "id" cfdi_template.
     *
     * @param id the id of the cfdi_template to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cfdi_template, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cfdi-templates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Cfdi_template> getCfdi_template(@PathVariable Long id) {
        log.debug("REST request to get Cfdi_template : {}", id);
        Cfdi_template cfdi_template = cfdi_templateService.findOne(id);
        return Optional.ofNullable(cfdi_template)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cfdi-templates/:id : delete the "id" cfdi_template.
     *
     * @param id the id of the cfdi_template to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cfdi-templates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCfdi_template(@PathVariable Long id) {
        log.debug("REST request to delete Cfdi_template : {}", id);
        cfdi_templateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cfdi_template", id.toString())).build();
    }

}
