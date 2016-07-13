package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_airline;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_airline entity.
 */
public interface Freecom_airlineRepository extends JpaRepository<Freecom_airline,Long> {

}
