package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_charge;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_charge entity.
 */
@SuppressWarnings("unused")
public interface Com_chargeRepository extends JpaRepository<Com_charge,Long> {

}
