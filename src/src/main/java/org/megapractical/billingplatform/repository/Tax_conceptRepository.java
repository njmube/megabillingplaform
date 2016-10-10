package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Tax_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tax_concept entity.
 */
@SuppressWarnings("unused")
public interface Tax_conceptRepository extends JpaRepository<Tax_concept,Long> {

}
