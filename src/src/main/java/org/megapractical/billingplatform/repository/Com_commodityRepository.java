package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_commodity;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_commodity entity.
 */
@SuppressWarnings("unused")
public interface Com_commodityRepository extends JpaRepository<Com_commodity,Long> {

}
