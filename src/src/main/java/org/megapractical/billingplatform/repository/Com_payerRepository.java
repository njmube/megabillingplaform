package org.megapractical.billingplatform.repository;

import org.megapractical.billingplatform.domain.Com_payer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Com_payer entity.
 */
@SuppressWarnings("unused")
public interface Com_payerRepository extends JpaRepository<Com_payer,Long> {

}
