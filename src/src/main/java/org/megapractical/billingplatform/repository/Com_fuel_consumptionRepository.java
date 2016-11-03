package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_fuel_consumption;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_fuel_consumption entity.
 */
@SuppressWarnings("unused")
public interface Com_fuel_consumptionRepository extends JpaRepository<Com_fuel_consumption,Long> {

}
