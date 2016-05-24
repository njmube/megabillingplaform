package org.megapractical.billingplatform.service.impl;

import org.megapractical.billingplatform.service.Category_productService;
import org.megapractical.billingplatform.domain.Category_product;
import org.megapractical.billingplatform.repository.Category_productRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Category_product.
 */
@Service
@Transactional
public class Category_productServiceImpl implements Category_productService{

    private final Logger log = LoggerFactory.getLogger(Category_productServiceImpl.class);
    
    @Inject
    private Category_productRepository category_productRepository;
    
    /**
     * Save a category_product.
     * 
     * @param category_product the entity to save
     * @return the persisted entity
     */
    public Category_product save(Category_product category_product) {
        log.debug("Request to save Category_product : {}", category_product);
        Category_product result = category_productRepository.save(category_product);
        return result;
    }

    /**
     *  Get all the category_products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Category_product> findAll(Pageable pageable) {
        log.debug("Request to get all Category_products");
        Page<Category_product> result = category_productRepository.findAll(pageable); 
        return result;
    }

    /**
     *  Get one category_product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Category_product findOne(Long id) {
        log.debug("Request to get Category_product : {}", id);
        Category_product category_product = category_productRepository.findOne(id);
        return category_product;
    }

    /**
     *  Delete the  category_product by id.
     *  
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Category_product : {}", id);
        category_productRepository.delete(id);
    }
}
