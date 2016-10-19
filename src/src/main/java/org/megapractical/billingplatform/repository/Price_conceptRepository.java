package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Price_concept;

import org.megapractical.billingplatform.domain.Taxpayer_concept;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Price_concept entity.
 */
@SuppressWarnings("unused")
public interface Price_conceptRepository extends JpaRepository<Price_concept,Long> {

}
