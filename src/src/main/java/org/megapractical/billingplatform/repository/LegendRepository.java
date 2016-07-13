package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Legend;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Legend entity.
 */
public interface LegendRepository extends JpaRepository<Legend,Long> {

}
