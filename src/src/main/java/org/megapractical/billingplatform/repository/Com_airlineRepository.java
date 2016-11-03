package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_airline;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_airline entity.
 */
@SuppressWarnings("unused")
public interface Com_airlineRepository extends JpaRepository<Com_airline,Long> {

}
