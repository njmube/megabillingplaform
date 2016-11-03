package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_local_taxes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_local_taxes entity.
 */
@SuppressWarnings("unused")
public interface Com_local_taxesRepository extends JpaRepository<Com_local_taxes,Long> {

}
