package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.C_location;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the C_location entity.
 */
@SuppressWarnings("unused")
public interface C_locationRepository extends JpaRepository<C_location,Long> {

}
