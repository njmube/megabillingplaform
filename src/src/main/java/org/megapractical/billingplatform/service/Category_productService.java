package org.megapractical.billingplatform.service;

import org.megapractical.billingplatform.domain.Category_product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing Category_product.
 */
public interface Category_productService {

    /**
     * Save a category_product.
     * 
     * @param category_product the entity to save
     * @return the persisted entity
     */
    Category_product save(Category_product category_product);

    /**
     *  Get all the category_products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Category_product> findAll(Pageable pageable);

    /**
     *  Get the "id" category_product.
     *  
     *  @param id the id of the entity
     *  @return the entity
     */
    Category_product findOne(Long id);

    /**
     *  Delete the "id" category_product.
     *  
     *  @param id the id of the entity
     */
    void delete(Long id);
}
