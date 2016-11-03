package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_taxregistration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_taxregistration entity.
 */
@SuppressWarnings("unused")
public interface Com_taxregistrationRepository extends JpaRepository<Com_taxregistration,Long> {

}
