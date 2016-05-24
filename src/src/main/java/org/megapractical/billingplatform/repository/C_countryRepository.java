package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_country;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_country entity.
 */
public interface C_countryRepository extends JpaRepository<C_country,Long> {

}
