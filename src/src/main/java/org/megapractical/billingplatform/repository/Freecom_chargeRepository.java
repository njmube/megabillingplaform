package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_charge;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_charge entity.
 */
public interface Freecom_chargeRepository extends JpaRepository<Freecom_charge,Long> {

}
