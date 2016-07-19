package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_country;
import org.megapractical.billingplatform.service.C_countryService;
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
 * REST controller for managing C_country.
 */
@RestController
@RequestMapping("/api")
public class C_countryResource {

    private final Logger log = LoggerFactory.getLogger(C_countryResource.class);

    @Inject
    private C_countryService c_countryService;

    /**
     * POST  /c-countries : Create a new c_country.
     *
     * @param c_country the c_country to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_country, or with status 400 (Bad Request) if the c_country has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-countries",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_country> createC_country(@RequestBody C_country c_country) throws URISyntaxException {
        log.debug("REST request to save C_country : {}", c_country);
        if (c_country.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_country", "idexists", "A new c_country cannot already have an ID")).body(null);
        }
        C_country result = c_countryService.save(c_country);
        return ResponseEntity.created(new URI("/api/c-countries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_country", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-countries : Updates an existing c_country.
     *
     * @param c_country the c_country to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_country,
     * or with status 400 (Bad Request) if the c_country is not valid,
     * or with status 500 (Internal Server Error) if the c_country couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-countries",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_country> updateC_country(@RequestBody C_country c_country) throws URISyntaxException {
        log.debug("REST request to update C_country : {}", c_country);
        if (c_country.getId() == null) {
            return createC_country(c_country);
        }
        C_country result = c_countryService.save(c_country);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_country", c_country.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-countries : get all the c_countries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_countries in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-countries",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"pg","filtername"})
    @Timed
    public ResponseEntity<List<C_country>> getAllC_countries(Pageable pageable,
                                                             @RequestParam(value = "pg") Integer pg,
                                                             @RequestParam(value = "filtername") String filtername
                                                             )
        throws URISyntaxException {
        log.debug("REST request to get a page of C_countries");
        if (pg == 0){
            if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
                Page<C_country> page = c_countryService.findAll(pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-countries");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }else {
                Page<C_country> page = c_countryService.findAllByName(filtername,pageable);
                HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-countries");
                return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
            }
        }else
        {
            if(filtername.compareTo(" ")==0 || filtername.isEmpty()) {
                List<C_country> page = c_countryService.findAll();
                return new ResponseEntity<>(page, HttpStatus.OK);
            }else {
                List<C_country> page = c_countryService.findAllByNameL(filtername);
                return new ResponseEntity<>(page, HttpStatus.OK);
            }
        }
    }

    /*@RequestMapping(value = "/c-countriesall",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_country>> getAllC_countries()
        throws URISyntaxException {
        log.debug("REST request to get a page of C_countries");
        List<C_country> page = c_countryService.findAll();
        return new ResponseEntity<>(page, HttpStatus.OK);
    }*/

    /**
     * GET  /c-countries/:id : get the "id" c_country.
     *
     * @param id the id of the c_country to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_country, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-countries/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_country> getC_country(@PathVariable Long id) {
        log.debug("REST request to get C_country : {}", id);
        C_country c_country = c_countryService.findOne(id);
        return Optional.ofNullable(c_country)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-countries/:id : delete the "id" c_country.
     *
     * @param id the id of the c_country to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-countries/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_country(@PathVariable Long id) {
        log.debug("REST request to delete C_country : {}", id);
        c_countryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_country", id.toString())).build();
    }

}
