package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Features_work_piece;
import org.megapractical.billingplatform.service.Features_work_pieceService;
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
 * REST controller for managing Features_work_piece.
 */
@RestController
@RequestMapping("/api")
public class Features_work_pieceResource {

    private final Logger log = LoggerFactory.getLogger(Features_work_pieceResource.class);
        
    @Inject
    private Features_work_pieceService features_work_pieceService;
    
    /**
     * POST  /features-work-pieces : Create a new features_work_piece.
     *
     * @param features_work_piece the features_work_piece to create
     * @return the ResponseEntity with status 201 (Created) and with body the new features_work_piece, or with status 400 (Bad Request) if the features_work_piece has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/features-work-pieces",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Features_work_piece> createFeatures_work_piece(@Valid @RequestBody Features_work_piece features_work_piece) throws URISyntaxException {
        log.debug("REST request to save Features_work_piece : {}", features_work_piece);
        if (features_work_piece.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("features_work_piece", "idexists", "A new features_work_piece cannot already have an ID")).body(null);
        }
        Features_work_piece result = features_work_pieceService.save(features_work_piece);
        return ResponseEntity.created(new URI("/api/features-work-pieces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("features_work_piece", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /features-work-pieces : Updates an existing features_work_piece.
     *
     * @param features_work_piece the features_work_piece to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated features_work_piece,
     * or with status 400 (Bad Request) if the features_work_piece is not valid,
     * or with status 500 (Internal Server Error) if the features_work_piece couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/features-work-pieces",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Features_work_piece> updateFeatures_work_piece(@Valid @RequestBody Features_work_piece features_work_piece) throws URISyntaxException {
        log.debug("REST request to update Features_work_piece : {}", features_work_piece);
        if (features_work_piece.getId() == null) {
            return createFeatures_work_piece(features_work_piece);
        }
        Features_work_piece result = features_work_pieceService.save(features_work_piece);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("features_work_piece", features_work_piece.getId().toString()))
            .body(result);
    }

    /**
     * GET  /features-work-pieces : get all the features_work_pieces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of features_work_pieces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/features-work-pieces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Features_work_piece>> getAllFeatures_work_pieces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Features_work_pieces");
        Page<Features_work_piece> page = features_work_pieceService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/features-work-pieces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /features-work-pieces/:id : get the "id" features_work_piece.
     *
     * @param id the id of the features_work_piece to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the features_work_piece, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/features-work-pieces/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Features_work_piece> getFeatures_work_piece(@PathVariable Long id) {
        log.debug("REST request to get Features_work_piece : {}", id);
        Features_work_piece features_work_piece = features_work_pieceService.findOne(id);
        return Optional.ofNullable(features_work_piece)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /features-work-pieces/:id : delete the "id" features_work_piece.
     *
     * @param id the id of the features_work_piece to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/features-work-pieces/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeatures_work_piece(@PathVariable Long id) {
        log.debug("REST request to delete Features_work_piece : {}", id);
        features_work_pieceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("features_work_piece", id.toString())).build();
    }

}
