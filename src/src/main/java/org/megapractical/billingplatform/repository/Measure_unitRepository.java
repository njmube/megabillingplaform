package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Measure_unit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Measure_unit entity.
 */
public interface Measure_unitRepository extends JpaRepository<Measure_unit,Long> {
    Page<Measure_unit> findByNameStartingWith(String filtername, Pageable pageable);

}
