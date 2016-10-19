package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Measure_unit_concept;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Measure_unit_concept entity.
 */
@SuppressWarnings("unused")
public interface Measure_unit_conceptRepository extends JpaRepository<Measure_unit_concept,Long> {

}
