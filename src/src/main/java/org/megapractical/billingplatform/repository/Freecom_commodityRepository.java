package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_commodity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_commodity entity.
 */
@SuppressWarnings("unused")
public interface Freecom_commodityRepository extends JpaRepository<Freecom_commodity,Long> {

}
