package org.megapractical.billingplatform.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.megapractical.billingplatform.domain.Category_product;
import org.megapractical.billingplatform.service.Category_productService;
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
 * REST controller for managing Category_product.
 */
@RestController
@RequestMapping("/api")
public class Category_productResource {

    private final Logger log = LoggerFactory.getLogger(Category_productResource.class);
        
    @Inject
    private Category_productService category_productService;
    
    /**
     * POST  /category-products : Create a new category_product.
     *
     * @param category_product the category_product to create
     * @return the ResponseEntity with status 201 (Created) and with body the new category_product, or with status 400 (Bad Request) if the category_product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/category-products",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Category_product> createCategory_product(@RequestBody Category_product category_product) throws URISyntaxException {
        log.debug("REST request to save Category_product : {}", category_product);
        if (category_product.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("category_product", "idexists", "A new category_product cannot already have an ID")).body(null);
        }
        Category_product result = category_productService.save(category_product);
        return ResponseEntity.created(new URI("/api/category-products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("category_product", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /category-products : Updates an existing category_product.
     *
     * @param category_product the category_product to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated category_product,
     * or with status 400 (Bad Request) if the category_product is not valid,
     * or with status 500 (Internal Server Error) if the category_product couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/category-products",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Category_product> updateCategory_product(@RequestBody Category_product category_product) throws URISyntaxException {
        log.debug("REST request to update Category_product : {}", category_product);
        if (category_product.getId() == null) {
            return createCategory_product(category_product);
        }
        Category_product result = category_productService.save(category_product);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("category_product", category_product.getId().toString()))
            .body(result);
    }

    /**
     * GET  /category-products : get all the category_products.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of category_products in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/category-products",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Category_product>> getAllCategory_products(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Category_products");
        Page<Category_product> page = category_productService.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/category-products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /category-products/:id : get the "id" category_product.
     *
     * @param id the id of the category_product to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the category_product, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/category-products/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Category_product> getCategory_product(@PathVariable Long id) {
        log.debug("REST request to get Category_product : {}", id);
        Category_product category_product = category_productService.findOne(id);
        return Optional.ofNullable(category_product)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /category-products/:id : delete the "id" category_product.
     *
     * @param id the id of the category_product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/category-products/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCategory_product(@PathVariable Long id) {
        log.debug("REST request to delete Category_product : {}", id);
        category_productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("category_product", id.toString())).build();
    }

}
