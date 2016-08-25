package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_complement;
import org.megapractical.billingplatform.service.C_complementService;
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
 * REST controller for managing C_complement.
 */
@RestController
@RequestMapping("/api")
public class C_complementResource {

    private final Logger log = LoggerFactory.getLogger(C_complementResource.class);

    @Inject
    private C_complementService c_complementService;

    /**
     * POST  /c-complements : Create a new c_complement.
     *
     * @param c_complement the c_complement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_complement, or with status 400 (Bad Request) if the c_complement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-complements",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_complement> createC_complement(@Valid @RequestBody C_complement c_complement) throws URISyntaxException {
        log.debug("REST request to save C_complement : {}", c_complement);
        if (c_complement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_complement", "idexists", "A new c_complement cannot already have an ID")).body(null);
        }
        C_complement result = c_complementService.save(c_complement);
        return ResponseEntity.created(new URI("/api/c-complements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_complement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-complements : Updates an existing c_complement.
     *
     * @param c_complement the c_complement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_complement,
     * or with status 400 (Bad Request) if the c_complement is not valid,
     * or with status 500 (Internal Server Error) if the c_complement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-complements",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_complement> updateC_complement(@Valid @RequestBody C_complement c_complement) throws URISyntaxException {
        log.debug("REST request to update C_complement : {}", c_complement);
        if (c_complement.getId() == null) {
            return createC_complement(c_complement);
        }
        C_complement result = c_complementService.save(c_complement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_complement", c_complement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-complements : get all the c_complements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_complements in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-complements",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"pg","filtername"})
    @Timed
    public ResponseEntity<List<C_complement>> getAllC_complements(Pageable pageable,
                                                                  @RequestParam(value = "pg") Integer pg,
                                                                  @RequestParam(value = "filtername") String filtername
    )
        throws URISyntaxException {
        log.debug("REST request to get a page of C_complements");
        if (pg == 0) {
            if (filtername.compareTo(" ") == 0 || filtername.isEmpty()) {
                Page<C_complement> page = c_complementService.findAll(pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-complements");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }else {
                Page<C_complement> page = c_complementService.findAllByName(filtername, pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-complements");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }else{
                if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
                    List<C_complement> page = c_complementService.findAll();
                    return new ResponseEntity<>(page, HttpStatus.OK);
                }else{
                    List<C_complement> page = c_complementService.findAllByNameL(filtername);
                    return new ResponseEntity<>(page, HttpStatus.OK);
                }
        }
    }

    /**
     * GET  /c-complements/:id : get the "id" c_complement.
     *
     * @param id the id of the c_complement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_complement, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-complements/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_complement> getC_complement(@PathVariable Long id) {
        log.debug("REST request to get C_complement : {}", id);
        C_complement c_complement = c_complementService.findOne(id);
        return Optional.ofNullable(c_complement)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-complements/:id : delete the "id" c_complement.
     *
     * @param id the id of the c_complement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-complements/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_complement(@PathVariable Long id) {
        log.debug("REST request to delete C_complement : {}", id);
        c_complementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_complement", id.toString())).build();
    }

}
