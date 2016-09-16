package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Client_address;
import org.megapractical.billingplatform.service.Client_addressService;
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
 * REST controller for managing Client_address.
 */
@RestController
@RequestMapping("/api")
public class Client_addressResource {

    private final Logger log = LoggerFactory.getLogger(Client_addressResource.class);
        
    @Inject
    private Client_addressService client_addressService;
    
    /**
     * POST  /client-addresses : Create a new client_address.
     *
     * @param client_address the client_address to create
     * @return the ResponseEntity with status 201 (Created) and with body the new client_address, or with status 400 (Bad Request) if the client_address has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/client-addresses",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client_address> createClient_address(@Valid @RequestBody Client_address client_address) throws URISyntaxException {
        log.debug("REST request to save Client_address : {}", client_address);
        if (client_address.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("client_address", "idexists", "A new client_address cannot already have an ID")).body(null);
        }
        Client_address result = client_addressService.save(client_address);
        return ResponseEntity.created(new URI("/api/client-addresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("client_address", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-addresses : Updates an existing client_address.
     *
     * @param client_address the client_address to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated client_address,
     * or with status 400 (Bad Request) if the client_address is not valid,
     * or with status 500 (Internal Server Error) if the client_address couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/client-addresses",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client_address> updateClient_address(@Valid @RequestBody Client_address client_address) throws URISyntaxException {
        log.debug("REST request to update Client_address : {}", client_address);
        if (client_address.getId() == null) {
            return createClient_address(client_address);
        }
        Client_address result = client_addressService.save(client_address);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("client_address", client_address.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-addresses : get all the client_addresses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of client_addresses in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/client-addresses",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Client_address>> getAllClient_addresses(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Client_addresses");
        Page<Client_address> page = client_addressService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-addresses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-addresses/:id : get the "id" client_address.
     *
     * @param id the id of the client_address to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the client_address, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/client-addresses/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Client_address> getClient_address(@PathVariable Long id) {
        log.debug("REST request to get Client_address : {}", id);
        Client_address client_address = client_addressService.findOne(id);
        return Optional.ofNullable(client_address)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /client-addresses/:id : delete the "id" client_address.
     *
     * @param id the id of the client_address to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/client-addresses/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClient_address(@PathVariable Long id) {
        log.debug("REST request to delete Client_address : {}", id);
        client_addressService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("client_address", id.toString())).build();
    }

}
