package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Concept entity.
 */
@SuppressWarnings("unused")
public interface ConceptRepository extends JpaRepository<Concept,Long> {

}
