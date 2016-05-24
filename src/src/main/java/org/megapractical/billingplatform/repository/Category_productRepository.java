package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Category_product;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Category_product entity.
 */
public interface Category_productRepository extends JpaRepository<Category_product,Long> {

}
