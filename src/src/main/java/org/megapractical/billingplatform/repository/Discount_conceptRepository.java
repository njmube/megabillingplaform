package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Discount_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Discount_concept entity.
 */
@SuppressWarnings("unused")
public interface Discount_conceptRepository extends JpaRepository<Discount_concept,Long> {

}
