package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Client_address;
import org.megapractical.billingplatform.domain.Taxpayer_client;
import org.megapractical.billingplatform.service.Client_addressService;
import org.megapractical.billingplatform.service.Taxpayer_clientService;
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
 * REST controller for managing Taxpayer_client.
 */
@RestController
@RequestMapping("/api")
public class Taxpayer_clientResource {

    private final Logger log = LoggerFactory.getLogger(Taxpayer_clientResource.class);

    @Inject
    private Taxpayer_clientService taxpayer_clientService;

    @Inject
    private Client_addressService client_addressService;

    /**
     * POST  /taxpayer-clients : Create a new taxpayer_client.
     *
     * @param taxpayer_client the taxpayer_client to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taxpayer_client, or with status 400 (Bad Request) if the taxpayer_client has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-clients",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_client> createTaxpayer_client(@Valid @RequestBody Taxpayer_client taxpayer_client) throws URISyntaxException {
        log.debug("REST request to save Taxpayer_client : {}", taxpayer_client);
        if (taxpayer_client.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("taxpayer_client", "idexists", "A new taxpayer_client cannot already have an ID")).body(null);
        }

        Client_address client_address = taxpayer_client.getClient_address();
        client_address = client_addressService.save(client_address);
        taxpayer_client.setClient_address(client_address);

        Taxpayer_client result = taxpayer_clientService.save(taxpayer_client);
        return ResponseEntity.created(new URI("/api/taxpayer-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("taxpayer_client", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /taxpayer-clients : Updates an existing taxpayer_client.
     *
     * @param taxpayer_client the taxpayer_client to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taxpayer_client,
     * or with status 400 (Bad Request) if the taxpayer_client is not valid,
     * or with status 500 (Internal Server Error) if the taxpayer_client couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/taxpayer-clients",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_client> updateTaxpayer_client(@Valid @RequestBody Taxpayer_client taxpayer_client) throws URISyntaxException {
        log.debug("REST request to update Taxpayer_client : {}", taxpayer_client);
        if (taxpayer_client.getId() == null) {
            return createTaxpayer_client(taxpayer_client);
        }

        Client_address client_address = taxpayer_client.getClient_address();
        client_address = client_addressService.save(client_address);
        taxpayer_client.setClient_address(client_address);

        Taxpayer_client result = taxpayer_clientService.save(taxpayer_client);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("taxpayer_client", taxpayer_client.getId().toString()))
            .body(result);
    }

    /**
     * GET  /taxpayer-clients : get all the taxpayer_clients.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of taxpayer_clients in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/taxpayer-clients",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE,
        params = {"taxpayeraccount", "rfc", "bussinesname", "email", "phone"})
    @Timed
    public ResponseEntity<List<Taxpayer_client>> getAllTaxpayer_clients(Pageable pageable,
                                                                        @RequestParam(value = "taxpayeraccount") Integer taxpayeraccount,
                                                                        @RequestParam(value = "rfc") String rfc,
                                                                        @RequestParam(value = "bussinesname") String bussinesname,
                                                                        @RequestParam(value = "email") String email,
                                                                        @RequestParam(value = "phone") String phone)
        throws URISyntaxException {
        log.debug("REST request to get a page of Taxpayer_clients");
        Page<Taxpayer_client> page = taxpayer_clientService.findAll(pageable, taxpayeraccount, rfc, bussinesname, email, phone);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/taxpayer-clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /taxpayer-clients/:id : get the "id" taxpayer_client.
     *
     * @param id the id of the taxpayer_client to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taxpayer_client, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/taxpayer-clients/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Taxpayer_client> getTaxpayer_client(@PathVariable Long id) {
        log.debug("REST request to get Taxpayer_client : {}", id);
        Taxpayer_client taxpayer_client = taxpayer_clientService.findOne(id);
        return Optional.ofNullable(taxpayer_client)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /taxpayer-clients/:id : delete the "id" taxpayer_client.
     *
     * @param id the id of the taxpayer_client to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/taxpayer-clients/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTaxpayer_client(@PathVariable Long id) {
        log.debug("REST request to delete Taxpayer_client : {}", id);
        taxpayer_clientService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("taxpayer_client", id.toString())).build();
    }

}
