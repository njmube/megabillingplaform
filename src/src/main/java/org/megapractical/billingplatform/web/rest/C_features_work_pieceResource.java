package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.C_features_work_piece;
import org.megapractical.billingplatform.service.C_features_work_pieceService;
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
 * REST controller for managing C_features_work_piece.
 */
@RestController
@RequestMapping("/api")
public class C_features_work_pieceResource {

    private final Logger log = LoggerFactory.getLogger(C_features_work_pieceResource.class);
        
    @Inject
    private C_features_work_pieceService c_features_work_pieceService;
    
    /**
     * POST  /c-features-work-pieces : Create a new c_features_work_piece.
     *
     * @param c_features_work_piece the c_features_work_piece to create
     * @return the ResponseEntity with status 201 (Created) and with body the new c_features_work_piece, or with status 400 (Bad Request) if the c_features_work_piece has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-features-work-pieces",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_features_work_piece> createC_features_work_piece(@Valid @RequestBody C_features_work_piece c_features_work_piece) throws URISyntaxException {
        log.debug("REST request to save C_features_work_piece : {}", c_features_work_piece);
        if (c_features_work_piece.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("c_features_work_piece", "idexists", "A new c_features_work_piece cannot already have an ID")).body(null);
        }
        C_features_work_piece result = c_features_work_pieceService.save(c_features_work_piece);
        return ResponseEntity.created(new URI("/api/c-features-work-pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("c_features_work_piece", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /c-features-work-pieces : Updates an existing c_features_work_piece.
     *
     * @param c_features_work_piece the c_features_work_piece to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated c_features_work_piece,
     * or with status 400 (Bad Request) if the c_features_work_piece is not valid,
     * or with status 500 (Internal Server Error) if the c_features_work_piece couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/c-features-work-pieces",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_features_work_piece> updateC_features_work_piece(@Valid @RequestBody C_features_work_piece c_features_work_piece) throws URISyntaxException {
        log.debug("REST request to update C_features_work_piece : {}", c_features_work_piece);
        if (c_features_work_piece.getId() == null) {
            return createC_features_work_piece(c_features_work_piece);
        }
        C_features_work_piece result = c_features_work_pieceService.save(c_features_work_piece);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("c_features_work_piece", c_features_work_piece.getId().toString()))
            .body(result);
    }

    /**
     * GET  /c-features-work-pieces : get all the c_features_work_pieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of c_features_work_pieces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/c-features-work-pieces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<C_features_work_piece>> getAllC_features_work_pieces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of C_features_work_pieces");
        Page<C_features_work_piece> page = c_features_work_pieceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/c-features-work-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /c-features-work-pieces/:id : get the "id" c_features_work_piece.
     *
     * @param id the id of the c_features_work_piece to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the c_features_work_piece, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/c-features-work-pieces/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<C_features_work_piece> getC_features_work_piece(@PathVariable Long id) {
        log.debug("REST request to get C_features_work_piece : {}", id);
        C_features_work_piece c_features_work_piece = c_features_work_pieceService.findOne(id);
        return Optional.ofNullable(c_features_work_piece)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /c-features-work-pieces/:id : delete the "id" c_features_work_piece.
     *
     * @param id the id of the c_features_work_piece to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/c-features-work-pieces/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteC_features_work_piece(@PathVariable Long id) {
        log.debug("REST request to delete C_features_work_piece : {}", id);
        c_features_work_pieceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("c_features_work_piece", id.toString())).build();
    }

}
