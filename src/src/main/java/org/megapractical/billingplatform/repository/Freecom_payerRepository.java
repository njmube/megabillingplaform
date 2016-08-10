package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Freecom_payer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Freecom_payer entity.
 */
@SuppressWarnings("unused")
public interface Freecom_payerRepository extends JpaRepository<Freecom_payer,Long> {

}
