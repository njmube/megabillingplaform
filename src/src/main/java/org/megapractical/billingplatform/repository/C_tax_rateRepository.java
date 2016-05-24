package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_tax_rate;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_tax_rate entity.
 */
public interface C_tax_rateRepository extends JpaRepository<C_tax_rate,Long> {

}
