package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Measure_unit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Measure_unit entity.
 */
public interface Measure_unitRepository extends JpaRepository<Measure_unit,Long> {

}
