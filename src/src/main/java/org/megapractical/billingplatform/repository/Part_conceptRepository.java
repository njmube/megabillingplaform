package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Part_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Part_concept entity.
 */
@SuppressWarnings("unused")
public interface Part_conceptRepository extends JpaRepository<Part_concept,Long> {

}
