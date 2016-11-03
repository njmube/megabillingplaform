package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_used_vehicle;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_used_vehicle entity.
 */
@SuppressWarnings("unused")
public interface Com_used_vehicleRepository extends JpaRepository<Com_used_vehicle,Long> {

}
