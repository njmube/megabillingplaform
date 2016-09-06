package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_local_taxes;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_local_taxes entity.
 */
@SuppressWarnings("unused")
public interface Freecom_local_taxesRepository extends JpaRepository<Freecom_local_taxes,Long> {

}
