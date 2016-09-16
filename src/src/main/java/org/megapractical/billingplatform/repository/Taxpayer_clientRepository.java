package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Taxpayer_client;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Taxpayer_client entity.
 */
@SuppressWarnings("unused")
public interface Taxpayer_clientRepository extends JpaRepository<Taxpayer_client,Long> {

}
