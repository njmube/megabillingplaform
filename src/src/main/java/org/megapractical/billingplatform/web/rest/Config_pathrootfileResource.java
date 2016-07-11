package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Config_pathrootfile;
import org.megapractical.billingplatform.service.Config_pathrootfileService;
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
 * REST controller for managing Config_pathrootfile.
 */
@RestController
@RequestMapping("/api")
public class Config_pathrootfileResource {

    private final Logger log = LoggerFactory.getLogger(Config_pathrootfileResource.class);

    @Inject
    private Config_pathrootfileService config_pathrootfileService;

    /**
     * POST  /config-pathrootfiles : Create a new config_pathrootfile.
     *
     * @param config_pathrootfile the config_pathrootfile to create
     * @return the ResponseEntity with status 201 (Created) and with body the new config_pathrootfile, or with status 400 (Bad Request) if the config_pathrootfile has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/config-pathrootfiles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Config_pathrootfile> createConfig_pathrootfile(@Valid @RequestBody Config_pathrootfile config_pathrootfile) throws URISyntaxException {
        log.debug("REST request to save Config_pathrootfile : {}", config_pathrootfile);
        if (config_pathrootfile.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("config_pathrootfile", "idexists", "A new config_pathrootfile cannot already have an ID")).body(null);
        }
        Config_pathrootfile result = config_pathrootfileService.save(config_pathrootfile);
        return ResponseEntity.created(new URI("/api/config-pathrootfiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("config_pathrootfile", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /config-pathrootfiles : Updates an existing config_pathrootfile.
     *
     * @param config_pathrootfile the config_pathrootfile to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated config_pathrootfile,
     * or with status 400 (Bad Request) if the config_pathrootfile is not valid,
     * or with status 500 (Internal Server Error) if the config_pathrootfile couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/config-pathrootfiles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Config_pathrootfile> updateConfig_pathrootfile(@Valid @RequestBody Config_pathrootfile config_pathrootfile) throws URISyntaxException {
        log.debug("REST request to update Config_pathrootfile : {}", config_pathrootfile);
        if (config_pathrootfile.getId() == null) {
            return createConfig_pathrootfile(config_pathrootfile);
        }
        Config_pathrootfile result = config_pathrootfileService.save(config_pathrootfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("config_pathrootfile", config_pathrootfile.getId().toString()))
            .body(result);
    }

    /**
     * GET  /config-pathrootfiles : get all the config_pathrootfiles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of config_pathrootfiles in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/config-pathrootfiles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Config_pathrootfile>> getAllConfig_pathrootfiles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Config_pathrootfiles");
        Page<Config_pathrootfile> page = config_pathrootfileService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/config-pathrootfiles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /config-pathrootfiles/:id : get the "id" config_pathrootfile.
     *
     * @param id the id of the config_pathrootfile to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the config_pathrootfile, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/config-pathrootfiles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Config_pathrootfile> getConfig_pathrootfile(@PathVariable Long id) {
        log.debug("REST request to get Config_pathrootfile : {}", id);
        Config_pathrootfile config_pathrootfile = null;
        List<Config_pathrootfile> list = config_pathrootfileService.finAll();
        if(list.size() == 0){
            config_pathrootfile = new Config_pathrootfile();
        }else {
            config_pathrootfile = list.get(0);
        }

        return Optional.ofNullable(config_pathrootfile)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /config-pathrootfiles/:id : delete the "id" config_pathrootfile.
     *
     * @param id the id of the config_pathrootfile to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/config-pathrootfiles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteConfig_pathrootfile(@PathVariable Long id) {
        log.debug("REST request to delete Config_pathrootfile : {}", id);
        config_pathrootfileService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("config_pathrootfile", id.toString())).build();
    }

}
